package com.travel.letsgospringboot.place.controller;

import com.travel.letsgospringboot.place.service.PlaceService;
import com.travel.letsgospringboot.place.vo.PlaceVO;
import com.travel.letsgospringboot.place.vo.VisitItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class PlaceRestController {

    private final PlaceService placeService;

    @GetMapping("/leisureListAjax")
    public List<PlaceVO> getLeisureListAjax(
            @RequestParam(value = "sortOrder", defaultValue = "distance") String sortOrder) {
        String sortBy = "like".equalsIgnoreCase(sortOrder) ? "like" : "title";
        return placeService.searchPlaces("LEISURE", null, null, sortBy);
    }

    @GetMapping("/placeLikeAjax")
    public Map<String, Object> likePlace(
            @RequestParam("placeId") String placeId,
            @RequestParam("placeType") String placeType) {

        Map<String, Object> response = new HashMap<>();
        try {
            placeService.setPlaceLikeCount(placeId);
            int updatedCount = placeService.getPlaceLikeCount(placeType, placeId);

            response.put("result", "success");
            response.put("likeCount", updatedCount);
        } catch (Exception e) {
            response.put("result", "fail");
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/controller")
    public ResponseEntity<?> handleControllerPost(
            @RequestParam("cmd") String cmd,
            @RequestParam(value = "placeId", required = false) String placeId,
            @RequestParam(value = "placeType", required = false) String placeType,
            HttpSession session) {

        if ("addCartAction".equals(cmd)) {
            if (placeId != null && !placeId.isEmpty()) {
                List<PlaceVO> cart = (List<PlaceVO>) session.getAttribute("placeCartList");
                if (cart == null) {
                    cart = new ArrayList<>();
                    session.setAttribute("placeCartList", cart);
                }
                boolean exists = false;
                for (PlaceVO p : cart) {
                    if (p.getPlaceId() != null && p.getPlaceId().toString().equals(placeId)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    PlaceVO place = placeService.getPlaceById(placeId);
                    if (place != null) {
                        place.setPlaceType(placeType != null ? placeType : "LEISURE");
                        cart.add(place);
                    }
                }
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Unknown command");
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/removeCartItemsAjax")
    public ResponseEntity<?> removeCartItems(
            @RequestParam("placeIds") String placeIds,
            HttpSession session) {

        List<PlaceVO> cart = (List<PlaceVO>) session.getAttribute("placeCartList");
        if (cart != null && placeIds != null && !placeIds.trim().isEmpty()) {
            String[] ids = placeIds.split(",");
            Set<String> idSet = new HashSet<>(Arrays.asList(ids));
            cart.removeIf(p -> p.getPlaceId() != null && idSet.contains(p.getPlaceId().toString()));
        }
        return ResponseEntity.ok().build();
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/addCartToScheduleAjax")
    public ResponseEntity<Map<String, Object>> addCartToSchedule(
            @RequestParam("placeIds") String placeIdsStr,
            @RequestParam(value = "myScheduleId", required = false) String myScheduleId,
            HttpSession session) {

        Map<String, Object> result = new HashMap<>();

        String loginUser = (String) session.getAttribute("loginOK");
        if (loginUser == null) {
            result.put("ok", false);
            result.put("message", "로그인이 필요합니다.");
            return ResponseEntity.ok(result);
        }

        if (placeIdsStr == null || placeIdsStr.trim().isEmpty()) {
            result.put("ok", false);
            result.put("message", "새로 담은 장소가 없습니다.");
            return ResponseEntity.ok(result);
        }

        String[] placeIds = placeIdsStr.split(",");
        List<String> addedPlaceIds = new ArrayList<>();
        int added = 0;

        String targetScheduleId = myScheduleId;
        try {
            if (targetScheduleId == null || targetScheduleId.trim().isEmpty()) {
                targetScheduleId = "SCH_" + UUID.randomUUID().toString().substring(0, 8);
                placeService.insertMySchedule(targetScheduleId, "새 일정", LocalDateTime.now(), loginUser);
            }

            List<VisitItemVO> existingItems = placeService.getVisitItemsByScheduleId(targetScheduleId);
            int nextOrder = existingItems.size() + 1;

            for (String pidStr : placeIds) {
                try {
                    Long placeId = Long.parseLong(pidStr.trim());
                    placeService.insertVisitItem(nextOrder, 0.0, placeId, targetScheduleId, "SCHEDULE");
                    addedPlaceIds.add(pidStr.trim());
                    added++;
                    nextOrder++;
                } catch (NumberFormatException nfe) {
                    // ignore invalid place id format
                }
            }

            List<PlaceVO> cart = (List<PlaceVO>) session.getAttribute("placeCartList");
            if (cart != null) {
                Set<String> idSet = new HashSet<>(addedPlaceIds);
                cart.removeIf(p -> p.getPlaceId() != null && idSet.contains(p.getPlaceId().toString()));
            }

            result.put("ok", true);
            result.put("added", added);
            result.put("addedPlaceIds", addedPlaceIds);

        } catch (Exception e) {
            result.put("ok", false);
            result.put("message", "일정 등록 중 오류가 발생했습니다: " + e.getMessage());
        }

        return ResponseEntity.ok(result);
    }
}
