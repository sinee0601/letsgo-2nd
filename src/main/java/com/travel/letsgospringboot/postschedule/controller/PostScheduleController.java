package com.travel.letsgospringboot.postschedule.controller;

import com.travel.letsgospringboot.common.PageResponse;
import com.travel.letsgospringboot.postschedule.service.PostScheduleService;
import com.travel.letsgospringboot.postschedule.vo.PostScheduleListTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/postschedule")
@RequiredArgsConstructor
public class PostScheduleController {

    private final PostScheduleService postScheduleService;

    @GetMapping("/list")
    public String postScheduleList(Model model,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "sortOrder", required = false) String sortOrder,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "12") int size){
        PageResponse<PostScheduleListTO> pageResponse = postScheduleService.getPostScheduleList(keyword, sortOrder, page, size);
        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("list", pageResponse.getContent());
        return "postScheduleList";
    }

    @GetMapping("/detail/{postId}")
    public String postScheduleDetail(@PathVariable("postId") String postId,
                                     Model model,
                                     Principal principal) {
        model.addAttribute("detail", postScheduleService.getPostScheduleDetail(postId, principal.getName()));;
        return  "postScheduleDetail";
    }
}
