package com.travel.letsgospringboot.place.controller;

import com.travel.letsgospringboot.place.service.PlaceService;
import com.travel.letsgospringboot.place.vo.PlaceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/")
    public String index(Model model) {
        List<PlaceVO> list = placeService.searchPlaces("LEISURE", null, null, "title");

        model.addAttribute("leisurePlaceList", list);

        return "placeview/html/index";
    }


    @GetMapping("/places/leisure")
    public String leisurePage(
            Model model,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortOrder", defaultValue = "distance") String sortOrder) {

        String sortBy = "popular".equalsIgnoreCase(sortOrder) ? "like" : "title";
        List<PlaceVO> list = placeService.searchPlaces("LEISURE", category, keyword, sortBy);


        model.addAttribute("leisurePlaceList", list);
        model.addAttribute("totalCount", list.size());

        return "placeview/html/leisure";
    }




    @GetMapping("/places/restaurant")
    public String restaurantPage(Model model,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortOrder", defaultValue = "name") String sortOrder) {

        String sortBy = "popular".equalsIgnoreCase(sortOrder) ? "like" : "title";
        List<PlaceVO> list = placeService.searchPlaces("RESTAURANT", category, keyword, sortBy);

        model.addAttribute("restaurantPlaceList", list);
        model.addAttribute("totalCount", list.size());

        return "placeview/html/restaurant";
    }

    @GetMapping("/places/stay")
    public String stayPage(Model model,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortOrder", defaultValue = "name") String sortOrder) {

        String sortBy = "popular".equalsIgnoreCase(sortOrder) ? "like" : "title";
        List<PlaceVO> list = placeService.searchPlaces("STAY", category, keyword, sortBy);

        model.addAttribute("stayPlaceList", list);
        model.addAttribute("totalCount", list.size());

        return "placeview/html/stay";
    }


    @GetMapping("/placeLikeAjax")
    @ResponseBody
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

}
