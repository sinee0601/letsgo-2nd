package com.travel.letsgospringboot.myschedule.controller;

import com.travel.letsgospringboot.myschedule.service.MyScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/myschedule")
@RequiredArgsConstructor
public class MyScheduleController {

    private final MyScheduleService myScheduleService;

    @GetMapping("/list")
    public String mySchedule(Model model, Principal principal) {
        model.addAttribute("myScheduleList", myScheduleService.getMyScheduleListAllByTitle(principal.getName()));
        return "myScheduleList";
    }

    @GetMapping("/detail/{scheduleId}")
    public String myScheduleDetail(Model model, @PathVariable String scheduleId) {
        model.addAttribute("schedule", myScheduleService.getScheduleDetail(scheduleId));
        model.addAttribute("scheduleRoute", myScheduleService.getScheduleRoute(scheduleId));
        return "myScheduleDetail";
    }
}
