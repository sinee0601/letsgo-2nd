package com.travel.letsgospringboot.admin.controller;

import com.travel.letsgospringboot.admin.service.AdminService;
import com.travel.letsgospringboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api")
@RequiredArgsConstructor
public class AdminRestController {
    private final AdminService adminService;
    private final UserService userService;

    @DeleteMapping("/places/{placeId}")
    public ResponseEntity<String> deletePlace(@PathVariable("placeId") Long placeId) {
        adminService.deletePlace(placeId);
        return ResponseEntity.ok("삭제되었습니다.");
    }

    @PostMapping("/places/{placeId}/toggle")
    public ResponseEntity<String> togglePlaceVisibility(@PathVariable("placeId") Long placeId,
                                                         @RequestParam("isActive") boolean isActive) {
        adminService.togglePlaceVisibility(placeId, isActive);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/users/{userID}/suspend")
    public ResponseEntity<String> suspendUser(@PathVariable("userID") String userID) {
        userService.suspendUser(userID);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/users/{userID}/unsuspend")
    public ResponseEntity<String> unsuspendUser(@PathVariable("userID") String userID) {
        userService.unsuspendUser(userID);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/reports/{reportId}/process")
    public ResponseEntity<String> processReport(@PathVariable("reportId") Long reportId,
                                                @RequestParam("action") String action) {
        adminService.processReport(reportId, action);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/posts/{postId}/toggle")
    public ResponseEntity<String> togglePostVisibility(@PathVariable("postId") String postId,
                                                        @RequestParam("isActive") boolean isActive) {
        adminService.togglePostVisibility(postId, isActive);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") String postId) {
        adminService.deletePost(postId);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
