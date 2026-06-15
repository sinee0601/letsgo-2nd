package com.travel.letsgospringboot.place.controller;

import com.travel.letsgospringboot.common.PageResponse;
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
    public String index(Model model,
                        @RequestParam(value = "sortOrder", defaultValue = "distance") String sortOrder) {
        String sortBy = "like".equalsIgnoreCase(sortOrder) ? "like" : "title";
        List<PlaceVO> list = placeService.searchPlaces("LEISURE", null, null, sortBy);

        model.addAttribute("leisurePlaceList", list);
        model.addAttribute("sortOrder", sortOrder);

        return "index";
    }


    @GetMapping("/places/leisure")
    public String leisurePage(Model model,
                              @RequestParam(value = "category", required = false) String category,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "sortOrder", defaultValue = "distance") String sortOrder,
                              @RequestParam(value = "page", defaultValue = "1") int page) {

        String sortBy = "popular".equalsIgnoreCase(sortOrder) ? "like" : "title";
        PageResponse<PlaceVO> leisure = placeService.getPlaceListPaged("LEISURE", category, keyword, sortBy, page, 20);


        model.addAttribute("leisurePlaceList", leisure);
        model.addAttribute("totalCount", leisure.getTotalElements());

        return "leisure";
    }




    @GetMapping("/places/restaurant")
    public String restaurantPage(Model model,
                                 @RequestParam(value = "category", required = false) String category,
                                 @RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "sortOrder", defaultValue = "name") String sortOrder,
                                 @RequestParam(value = "page", defaultValue = "1") int page) {

        String sortBy = "popular".equalsIgnoreCase(sortOrder) ? "like" : "title";
        PageResponse<PlaceVO> restaurant = placeService.getPlaceListPaged("RESTAURANT", category, keyword, sortBy, page, 20);

        model.addAttribute("restaurantPlaceList", restaurant);
        model.addAttribute("totalCount", restaurant.getTotalElements());

        return "restaurant";
    }

    @GetMapping("/places/stay")
    public String stayPage(Model model,
                           @RequestParam(value = "category", required = false) String category,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           @RequestParam(value = "sortOrder", defaultValue = "name") String sortOrder,
                           @RequestParam(value = "page", defaultValue = "1") int page) {

        String sortBy = "popular".equalsIgnoreCase(sortOrder) ? "like" : "title";
        PageResponse<PlaceVO> stay = placeService.getPlaceListPaged("STAY", category, keyword, sortBy, page, 20);

        model.addAttribute("stayPlaceList", stay);
        model.addAttribute("totalCount", stay.getTotalElements());

        return "stay";
    }


}
