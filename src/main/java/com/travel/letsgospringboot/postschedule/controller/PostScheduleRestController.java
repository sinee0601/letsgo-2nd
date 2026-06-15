package com.travel.letsgospringboot.postschedule.controller;

import com.travel.letsgospringboot.common.PageResponse;
import com.travel.letsgospringboot.postschedule.service.PostScheduleService;
import com.travel.letsgospringboot.postschedule.vo.MapScheduleTO;
import com.travel.letsgospringboot.postschedule.vo.PostScheduleListTO;
import com.travel.letsgospringboot.postschedule.vo.RouteScheduleTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
                                                                @RequestParam(defaultValue = "1") int page,
                                                                @RequestParam(defaultValue = "12") int size) {
        return postScheduleService.getPostScheduleList(keyword, sortOrder, page, size);
    }

    @GetMapping("/mylist")
    public PageResponse<PostScheduleListTO> getUserPostScheduleList(Principal principal,
                                                                    @RequestParam(value = "keyword", required = false) String keyword,
                                                                    @RequestParam(value = "sortOrder", required = false) String sortOrder,
                                                                    @RequestParam(defaultValue = "1") int page,
                                                                    @RequestParam(defaultValue = "12") int size) {
        return postScheduleService.getUserPostScheduleList(principal.getName(), keyword, sortOrder, page, size);
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

    @GetMapping("/{postId}/title")
    public String getScheduleTitle(@PathVariable("postId") String postId) {
        return postScheduleService.getScheduleTitle(postId);
    }

    @GetMapping("/{postId}/id")
    public String getUserId(@PathVariable("postId") String postId) {
        return postScheduleService.getUserId(postId);
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
    public ResponseEntity<Void> deletePostSchedule(Principal principal, @PathVariable("postId") String postId) {
        postScheduleService.deletePostSchedule(postId, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{postId}/copy")
    ResponseEntity<Void> addToMySchedule(Principal principal, @PathVariable("postId") String postId) {
        postScheduleService.addToMySchedule(postId, principal.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/report")
    public ResponseEntity<Void> reportPostSchedule(Principal principal, @PathVariable("postId") String postId, @RequestBody Map<String, String> body) {
        postScheduleService.reportPostSchedule(postId, principal.getName(), body.get("reason"));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
