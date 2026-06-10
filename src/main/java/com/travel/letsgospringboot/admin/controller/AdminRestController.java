package com.travel.letsgospringboot.admin.controller;

import com.travel.letsgospringboot.admin.service.AdminService;
import com.travel.letsgospringboot.place.service.PlaceService;
import com.travel.letsgospringboot.postschedule.service.PostScheduleService;
import com.travel.letsgospringboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api")
@RequiredArgsConstructor
public class AdminRestController {

    private final AdminService adminService;
    private final UserService userService;
    private final PlaceService placeService;
    private final PostScheduleService postScheduleService;



}
