package com.travel.letsgospringboot.myschedule.controller;

import com.travel.letsgospringboot.myschedule.service.MyScheduleService;
import jakarta.servlet.http.HttpSession;
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
    public String myScheduleDetail(Model model, @PathVariable String scheduleId, Principal principal
            , HttpSession session) {
        session.removeAttribute("fromScheduleMode");
        session.removeAttribute("currentScheduleId");
        session.removeAttribute("lockedCartItems");

        model.addAttribute("schedule", myScheduleService.getScheduleDetail(scheduleId, principal.getName()));
        model.addAttribute("scheduleRoute", myScheduleService.getScheduleRoute(scheduleId, principal.getName()));
        return "myScheduleDetail";
    }

    @GetMapping("/detail/{scheduleId}/addVisit")
    public String addVisitToSchedule(@PathVariable String scheduleId,
                                     Principal principal, HttpSession session) {
        session.setAttribute("fromScheduleMode", true);
        session.setAttribute("currentScheduleId", scheduleId);
        session.setAttribute("lockedCartItems", myScheduleService.getScheduleRoute(scheduleId, principal.getName()));
        session.removeAttribute("placeCartList");
        return "redirect:/places/leisure";
    }
}
