package com.travel.letsgospringboot.postschedule.controller;

import com.travel.letsgospringboot.common.PageResponse;
import com.travel.letsgospringboot.postschedule.service.PostScheduleService;
import com.travel.letsgospringboot.postschedule.vo.PostScheduleListTO;
import com.travel.letsgospringboot.user.auth.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/postschedule")
@RequiredArgsConstructor
public class PostScheduleController {

    private final PostScheduleService postScheduleService;

    @GetMapping("/list")
    public String postScheduleList(Model model,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "sortOrder", required = false) String sortOrder,
                                   @RequestParam(defaultValue = "1") int page){
        PageResponse<PostScheduleListTO> pageResponse = postScheduleService.getPostScheduleList(keyword, sortOrder, page, 12);
        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("list", pageResponse.getContent());
        return "postScheduleList";
    }

    @GetMapping("/detail/{postId}")
    public String postScheduleDetail(@PathVariable("postId") String postId,
                                     Model model,
                                     @AuthenticationPrincipal AppUserDetails userDetails) {
        model.addAttribute("detail", postScheduleService.getPostScheduleDetail(postId, userDetails.getUsername()));;
        return  "postScheduleDetail";
    }
}
