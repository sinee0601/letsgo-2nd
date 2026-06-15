package com.travel.letsgospringboot.admin.controller;

import com.travel.letsgospringboot.admin.service.AdminService;
import com.travel.letsgospringboot.admin.vo.AdminPostVO;
import com.travel.letsgospringboot.place.vo.PlaceVO;
import com.travel.letsgospringboot.user.repository.JpaUsers;
import com.travel.letsgospringboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("totalUsers", adminService.getTotalUserCount());
        model.addAttribute("totalPlaces", adminService.getTotalPlaceCount());
        model.addAttribute("totalSchedules", adminService.getTotalScheduleCount());
        model.addAttribute("recentPlaces", adminService.getRecentPlaces());
        return "admin/dashboard";
    }

    @PostMapping("/places/new")
    public String insertPlace(PlaceVO placeVO) {
        adminService.insertPlace(placeVO);
        return "redirect:/admin/places";
    }

    @PostMapping("/places/edit")
    public String editPlace(PlaceVO placeVO) {
        adminService.updatePlace(placeVO);
        return "redirect:/admin/places";
    }

    @GetMapping("/users")
    public String adminUsers(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<JpaUsers> users;
        if (keyword != null && !keyword.trim().isEmpty()) {
            users = userService.searchUsers(keyword);
        } else {
            users = userService.getAllUsers();
        }
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/users/warning")
    public String giveWarning(@RequestParam("userId") String userId,
            @RequestParam("reason") String reason) {
        userService.giveWarning(userId, reason);
        return "redirect:/admin/users";
    }

    @GetMapping("/places")
    public String adminPlaces(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<PlaceVO> places;
        if (keyword != null && !keyword.trim().isEmpty()) {
            places = adminService.searchPlaces(keyword);
        } else {
            places = adminService.getAllPlaces();
        }
        model.addAttribute("places", places);
        return "admin/places";
    }

    @GetMapping("/posts")
    public String adminPosts(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<AdminPostVO> posts;
        if (keyword != null && !keyword.trim().isEmpty()) {
            posts = adminService.searchPosts(keyword);
        } else {
            posts = adminService.getAllPosts();
        }
        model.addAttribute("posts", posts);
        return "admin/posts";
    }

    @GetMapping("/reports")
    public String adminReports(Model model) {
        model.addAttribute("reports", adminService.getAllReports());
        return "admin/reports";
    }
}
