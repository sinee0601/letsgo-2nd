package com.travel.letsgospringboot.admin.controller;

import com.travel.letsgospringboot.admin.service.AdminService;
import com.travel.letsgospringboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        try {
            adminService.deletePlace(placeId);
            return ResponseEntity.ok("삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("삭제에 실패했습니다.");
        }
    }

    @PostMapping("/places/{placeId}/toggle")
    public ResponseEntity<String> togglePlaceVisibility(@PathVariable("placeId") Long placeId,
                                                         @RequestParam("isActive") boolean isActive) {
        try {
            adminService.togglePlaceVisibility(placeId, isActive);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("상태 변경에 실패했습니다.");
        }
    }

    @PostMapping("/users/{userID}/suspend")
    public ResponseEntity<String> suspendUser(@PathVariable("userID") String userID) {
        try {
            userService.suspendUser(userID);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/users/{userID}/unsuspend")
    public ResponseEntity<String> unsuspendUser(@PathVariable("userID") String userID) {
        try {
            userService.unsuspendUser(userID);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/reports/{reportId}/process")
    public ResponseEntity<String> processReport(@PathVariable("reportId") Long reportId,
                                                @RequestParam("action") String action) {
        try {
            adminService.processReport(reportId, action);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("처리에 실패했습니다.");
        }
    }

    @PostMapping("/posts/{postId}/toggle")
    public ResponseEntity<String> togglePostVisibility(@PathVariable("postId") String postId,
                                                        @RequestParam("isActive") boolean isActive) {
        try {
            adminService.togglePostVisibility(postId, isActive);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("상태 변경에 실패했습니다.");
        }
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") String postId) {
        try {
            adminService.deletePost(postId);
            return ResponseEntity.ok("삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("삭제에 실패했습니다.");
        }
    }
}
