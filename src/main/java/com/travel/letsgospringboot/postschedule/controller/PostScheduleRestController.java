package com.travel.letsgospringboot.postschedule.controller;

import com.travel.letsgospringboot.postschedule.service.PostScheduleService;
import com.travel.letsgospringboot.postschedule.vo.MapScheduleTO;
import com.travel.letsgospringboot.postschedule.vo.PostScheduleListTO;
import com.travel.letsgospringboot.postschedule.vo.RouteScheduleTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public List<PostScheduleListTO> getPostScheduleList(@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "keyword", required = false) String keyword) {
        if (sortOrder == null || sortOrder.trim().isEmpty()) {
            sortOrder = "latest";
        }
        if (keyword == null || keyword.trim().isEmpty()) {
            switch (sortOrder) {
                case "like":
                    return postScheduleService.getPostScheduleListLike();
                case "view":
                    return postScheduleService.getPostScheduleListView();
                case "title":
                    return postScheduleService.getPostScheduleListTitle();
                default:
                    return postScheduleService.getPostScheduleListLatest();
            }
        } else {
            switch (sortOrder) {
                case "like":
                    return postScheduleService.getPostScheduleListSearchLike(keyword);
                case "view":
                    return postScheduleService.getPostScheduleListSearchView(keyword);
                case "title":
                    return postScheduleService.getPostScheduleListSearchTitle(keyword);
                default:
                    return postScheduleService.getPostScheduleListSearchLatest(keyword);
            }
        }
    }

    @GetMapping("/mylist")
    public List<PostScheduleListTO> getUserPostScheduleList(Principal principal, @RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "keyword", required = false) String keyword) {
        String userId = principal.getName();
        if (sortOrder == null || sortOrder.trim().isEmpty()) {
            sortOrder = "latest";
        }
        if (keyword == null || keyword.trim().isEmpty()) {
            switch (sortOrder) {
                case "like":
                    return postScheduleService.getUserPostScheduleListLike(userId);
                case "view":
                    return postScheduleService.getUserPostScheduleListView(userId);
                case "title":
                    return postScheduleService.getUserPostScheduleListTitle(userId);
                default:
                    return postScheduleService.getUserPostScheduleListLatest(userId);
            }
        } else {
            switch (sortOrder) {
                case "like":
                    return postScheduleService.getUserPostScheduleListSearchLike(userId, keyword);
                case "view":
                    return postScheduleService.getUserPostScheduleListSearchView(userId, keyword);
                case "title":
                    return postScheduleService.getUserPostScheduleListSearchTitle(userId, keyword);
                default:
                    return postScheduleService.getUserPostScheduleListSearchLatest(userId, keyword);
            }
        }
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
    public int plusLike(@PathVariable("postId") String postId) {
        return postScheduleService.plusLike(postId);
    }

    @PutMapping("/{postId}/plusView")
    public int plusView(@PathVariable("postId") String postId) {
        return postScheduleService.plusView(postId);
    }

    @DeleteMapping("/{postId}")
    public void deletePostSchedule(Principal principal, @PathVariable("postId") String postId){
        String loginUserId = principal.getName();
        postScheduleService.deletePostSchedule(postId, loginUserId);
    }

    @PutMapping("/{postId}/copy")
    public void addToMySchedule(Principal principal, @PathVariable("postId") String postId) {
        postScheduleService.addToMySchedule(postId, principal.getName());
    }

    @PostMapping("/{postId}/report")
    public ResponseEntity<Void> reportPostSchedule(Principal principal, @PathVariable("postId") String postId, @RequestBody Map<String, String> body) {
        postScheduleService.reportPostSchedule(postId, principal.getName(), body.get("reason")
        );

        return ResponseEntity.ok().build();
    }
}
