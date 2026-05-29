package com.travel.letsgospringboot.postschedule.controller;

import com.travel.letsgospringboot.postschedule.service.PostScheduleService;
import com.travel.letsgospringboot.postschedule.vo.PostScheduleTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/postschedule")
@RequiredArgsConstructor
public class PostScheduleController {

    private final PostScheduleService postScheduleService;

    @GetMapping()
    @ResponseBody
    public List<PostScheduleTO> postScheduleTOList(@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "keyword", required = false) String keyword) {
        if (sortOrder == null || sortOrder.trim().isEmpty()) {
            sortOrder = "latest";
        }
        System.out.println(sortOrder + keyword);
        if (keyword == null || keyword.trim().isEmpty()) {
            switch (sortOrder) {
                case "like":
                    return postScheduleService.getPostScheduleListLike();
                case "view":
                    return postScheduleService.getPostScheduleListView();
                case "title":
                    return postScheduleService.getPostScheduleListTitle();
                default:
                    return postScheduleService.getPostScheduleListLatest();
            }
        } else {
            switch (sortOrder) {
                case "like":
                    return postScheduleService.getPostScheduleListSearchLike(keyword);
                case "view":
                    return postScheduleService.getPostScheduleListSearchView(keyword);
                case "title":
                    return postScheduleService.getPostScheduleListSearchTitle(keyword);
                default:
                    return postScheduleService.getPostScheduleListSearchLatest(keyword);
            }
        }
    }


}
