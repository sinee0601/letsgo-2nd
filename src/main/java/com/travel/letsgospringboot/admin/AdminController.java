package com.travel.letsgospringboot.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String admin() {
        return "admin/dashboard";
    }

    @GetMapping("/places")
    public String adminPlaces() {
        return "admin/places";
    }

    @GetMapping("/posts")
    public String adminPosts() {
        return "admin/posts";
    }

    @GetMapping("/reports")
    public String adminReports() {
        return "admin/reports";
    }

    @GetMapping("/users")
    public String adminUsers() {
        return "admin/users";
    }
}
