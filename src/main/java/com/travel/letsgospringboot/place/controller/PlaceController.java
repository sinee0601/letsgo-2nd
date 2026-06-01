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


    @GetMapping("/places/leisure")
    @ResponseBody
    public List<PlaceVO> leisurePage(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortOrder", defaultValue = "distance") String sortOrder) {

        String sortBy = "popular".equalsIgnoreCase(sortOrder) ? "like" : "title";
        List<PlaceVO> list = placeService.searchPlaces("LEISURE", category, keyword, sortBy);

        return list;
    }


    @GetMapping("/places/restaurant")
    @ResponseBody
    public List<PlaceVO> restaurantPage(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortOrder", defaultValue = "name") String sortOrder) {

        String sortBy = "popular".equalsIgnoreCase(sortOrder) ? "like" : "title";
        List<PlaceVO> list = placeService.searchPlaces("RESTAURANT", category, keyword, sortBy);

        return list;
    }


    @GetMapping("/places/stay")
    @ResponseBody
    public List<PlaceVO> stayPage(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortOrder", defaultValue = "name") String sortOrder) {

        String sortBy = "popular".equalsIgnoreCase(sortOrder) ? "like" : "title";
        List<PlaceVO> list = placeService.searchPlaces("STAY", category, keyword, sortBy);

        return list;
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


