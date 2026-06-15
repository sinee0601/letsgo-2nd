package com.travel.letsgospringboot.myschedule.controller;

import com.travel.letsgospringboot.myschedule.service.MyScheduleService;
import com.travel.letsgospringboot.user.auth.AppUserDetails;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String mySchedule() {
        return "myScheduleList";
    }

    @GetMapping("/detail/{scheduleId}")
    public String myScheduleDetail(Model model, @PathVariable String scheduleId, @AuthenticationPrincipal AppUserDetails userDetails
            , HttpSession session) {
        session.removeAttribute("fromScheduleMode");
        session.removeAttribute("currentScheduleId");
        session.removeAttribute("lockedCartItems");

        model.addAttribute("schedule", myScheduleService.getScheduleDetail(scheduleId, userDetails.getUsername()));
        model.addAttribute("scheduleRoute", myScheduleService.getScheduleRoute(scheduleId, userDetails.getUsername()));
        return "myScheduleDetail";
    }

    @GetMapping("/detail/{scheduleId}/addVisit")
    public String addVisitToSchedule(@PathVariable String scheduleId,
                                     @AuthenticationPrincipal AppUserDetails userDetails, HttpSession session) {
        session.setAttribute("fromScheduleMode", true);
        session.setAttribute("currentScheduleId", scheduleId);
        session.setAttribute("lockedCartItems", myScheduleService.getScheduleRoute(scheduleId, userDetails.getUsername()));
        session.removeAttribute("placeCartList");
        return "redirect:/places/leisure";
    }
}
