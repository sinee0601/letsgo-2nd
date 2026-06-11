package com.travel.letsgospringboot.myschedule.controller;

import com.travel.letsgospringboot.myschedule.service.MyScheduleService;
import com.travel.letsgospringboot.myschedule.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/myschedule/api")
@RequiredArgsConstructor
public class MyScheduleRestController {

    private final MyScheduleService myScheduleService;

    @GetMapping("/list")
    public List<MyScheduleVO> getMyScheduleList(
            @RequestParam(defaultValue = "") String searchTitle,
            @RequestParam(defaultValue = "date") String sortOrder,
            @RequestParam(defaultValue = "false") boolean isShared,
            Principal principal) {

        String userId = principal.getName();
        boolean isSortTitle = "title".equals(sortOrder);
        boolean hasKeyword = !searchTitle.isEmpty();

        if (isShared) {
            if (hasKeyword) {
                return isSortTitle
                        ? myScheduleService.getMyScheduleListSearchSharedByTitle(userId, searchTitle)
                        : myScheduleService.getMyScheduleListSearchSharedByDate(userId, searchTitle);
            }
            return isSortTitle
                    ? myScheduleService.getMyScheduleListSharedByTitle(userId)
                    : myScheduleService.getMyScheduleListSharedByDate(userId);
        }

        if (hasKeyword) {
            return isSortTitle
                    ? myScheduleService.getMyScheduleListSearchByTitle(userId, searchTitle)
                    : myScheduleService.getMyScheduleListSearchByDate(userId, searchTitle);
        }

        return isSortTitle
                ? myScheduleService.getMyScheduleListAllByTitle(userId)
                : myScheduleService.getMyScheduleListAllByDate(userId);
    }

    @PostMapping
    public boolean addMySchedule(@RequestParam String scheduleId,
                                 @RequestParam String title,
                                 Principal principal) {


        return myScheduleService.addMySchedule(scheduleId, title, principal.getName());
    }

    @DeleteMapping("/{scheduleId}")
    public boolean deleteMySchedule(@PathVariable String scheduleId, Principal principal) {
        return myScheduleService.deleteMySchedule(scheduleId, principal.getName());
    }

    @PutMapping("/{scheduleId}/title")
    public boolean setMyScheduleTitle(@PathVariable String scheduleId,
                                      @RequestParam String title,
                                      Principal principal) {
        return myScheduleService.setMyScheduleTitle(title, scheduleId, principal.getName());
    }

    @PutMapping("/{scheduleId}/startAt")
    public boolean setStartAt(@PathVariable String scheduleId,
                              @RequestParam String startAt,
                              Principal principal) {
        return myScheduleService.setStartAt(scheduleId, startAt, principal.getName());
    }

    @PutMapping("/{scheduleId}/todo")
    public boolean setTodoDetail(@PathVariable String scheduleId,
                                 @RequestParam String todoDetail,
                                 Principal principal) {
        return myScheduleService.setTodoDetail(scheduleId, todoDetail, principal.getName());
    }

    @PutMapping("/{scheduleId}/budget")
    public boolean setBudgetDetail(@PathVariable String scheduleId,
                                   @RequestParam String budgetDetail,
                                   Principal principal) {
        return myScheduleService.setBudgetDetail(scheduleId, budgetDetail, principal.getName());
    }

    @PostMapping("/{scheduleId}/share")
    public String shareToPost(@PathVariable String scheduleId,
                              @RequestParam int isAnonymous,
                              Principal principal) {
        return myScheduleService.shareToPost(scheduleId, principal.getName(), isAnonymous);
    }

    @GetMapping("/{scheduleId}/companion")
    public List<ColleagueVO> getCompanionList(@PathVariable String scheduleId,
                                              Principal principal) {
        return myScheduleService.getCompanionList(scheduleId, principal.getName());
    }

    @PostMapping("/{scheduleId}/companion")
    public boolean addCompanion(@PathVariable String scheduleId,
                                @RequestParam String sharedUserId) {
        return myScheduleService.addCompanion(scheduleId, sharedUserId);
    }

    @PutMapping("/{scheduleId}/companion/permission")
    public boolean setCompanionPermission(@PathVariable String scheduleId,
                                          @RequestParam String sharedUserId,
                                          @RequestParam String permission,
                                          Principal principal) {
        return myScheduleService.setCompanionPermission(scheduleId, sharedUserId, permission, principal.getName());
    }

    @PostMapping("/{scheduleId}/visit")
    public boolean addVisitItem(@PathVariable String scheduleId,
                                @RequestParam int visitOrder,
                                @RequestParam String placeId,
                                Principal principal) {
        return myScheduleService.addVisitItem(visitOrder, placeId, scheduleId, principal.getName());
    }

    @PutMapping("/{scheduleId}/visit/orders")
    public boolean updateVisitOrders(@PathVariable String scheduleId,
                                     @RequestBody List<VisitOrderVO> request,
                                     Principal principal) {
        return myScheduleService.updateVisitOrders(request, scheduleId, principal.getName());
    }

    @DeleteMapping("/{scheduleId}/visit/{visitItemId}")
    public boolean deleteVisitItemById(@PathVariable String scheduleId,
                                       @PathVariable String visitItemId,
                                       Principal principal) {
        boolean bool = myScheduleService.deleteVisitItemById(visitItemId, scheduleId, principal.getName());
//        log.info("결과", bool);
        return bool;
    }

    @GetMapping("/{scheduleId}/mapSchedule")
    public ResponseEntity<List<MapScheduleVO>> getMapSchedule(@PathVariable String scheduleId, Principal principal){
        List<MapScheduleVO> mapSchedule = myScheduleService.getMapSchedule(scheduleId, principal.getName());
        return ResponseEntity.ok(mapSchedule);
    }

}
