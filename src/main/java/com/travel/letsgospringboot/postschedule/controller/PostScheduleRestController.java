package com.travel.letsgospringboot.postschedule.controller;

import com.travel.letsgospringboot.common.PageResponse;
import com.travel.letsgospringboot.postschedule.service.PostScheduleService;
import com.travel.letsgospringboot.postschedule.vo.MapScheduleTO;
import com.travel.letsgospringboot.postschedule.vo.PostScheduleListTO;
import com.travel.letsgospringboot.postschedule.vo.RouteScheduleTO;
import com.travel.letsgospringboot.user.auth.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/postschedule/api")
@RequiredArgsConstructor
public class PostScheduleRestController {

    private final PostScheduleService postScheduleService;
    @GetMapping("/list")
    public PageResponse<PostScheduleListTO> getPostScheduleList(@RequestParam(value = "keyword", required = false) String keyword,
                                                                @RequestParam(value = "sortOrder", required = false) String sortOrder,
                                                                @RequestParam(defaultValue = "1") int page) {
        return postScheduleService.getPostScheduleList(keyword, sortOrder, page, 12);
    }

    @GetMapping("/mylist")
    public PageResponse<PostScheduleListTO> getUserPostScheduleList(@AuthenticationPrincipal AppUserDetails userDetails,
                                                                    @RequestParam(value = "keyword", required = false) String keyword,
                                                                    @RequestParam(value = "sortOrder", required = false) String sortOrder,
                                                                    @RequestParam(defaultValue = "1") int page) {
        return postScheduleService.getUserPostScheduleList(userDetails.getUsername(), keyword, sortOrder, page, 12);
    }

    @GetMapping("/{postId}/budget")
    public String getBudgetDetail(@PathVariable("postId") String postId) {
        return postScheduleService.getBudgetDetail(postId);
    }

    @GetMapping("/{postId}/todo")
    public String getTodoDetail(@PathVariable("postId") String postId) {
        return postScheduleService.getTodoDetail(postId);
    }

    @GetMapping("/{postId}/route")
    public List<RouteScheduleTO> getScheduleRoute(@PathVariable("postId") String postId) {
        return postScheduleService.getScheduleRoute(postId);
    }

    @GetMapping("/{postId}/map")
    public List<MapScheduleTO> getMapSchedule(@PathVariable("postId") String postId) {
        return postScheduleService.getMapSchedule(postId);
    }

    @PutMapping("/{postId}/plusLike")
    public ResponseEntity<Integer> plusLike(@PathVariable("postId") String postId) {
        int likeCount = postScheduleService.plusLike(postId);
        return ResponseEntity.ok(likeCount);
    }

    @PutMapping("/{postId}/plusView")
    public ResponseEntity<Integer> plusView(@PathVariable("postId") String postId) {
        int viewCount = postScheduleService.plusView(postId);
        return ResponseEntity.ok(viewCount);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostSchedule(@AuthenticationPrincipal AppUserDetails userDetails, @PathVariable("postId") String postId) {
        postScheduleService.deletePostSchedule(postId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{postId}/copy")
    ResponseEntity<Void> addToMySchedule(@AuthenticationPrincipal AppUserDetails userDetails, @PathVariable("postId") String postId) {
        postScheduleService.addToMySchedule(postId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/report")
    public ResponseEntity<Void> reportPostSchedule(@AuthenticationPrincipal AppUserDetails userDetails, @PathVariable("postId") String postId, @RequestBody Map<String, String> body) {
        postScheduleService.reportPostSchedule(postId, userDetails.getUsername(), body.get("reason"));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
