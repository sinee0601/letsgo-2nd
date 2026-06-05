package com.travel.letsgospringboot.postschedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/postschedule")
public class PostScheduleController {

    @GetMapping("/list")
    public String postScheduleList(){
        return "postScheduleList";
    }
}
