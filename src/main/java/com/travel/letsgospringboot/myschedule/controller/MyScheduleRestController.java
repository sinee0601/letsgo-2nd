package com.travel.letsgospringboot.myschedule.controller;

import com.travel.letsgospringboot.myschedule.service.MyScheduleService;
import com.travel.letsgospringboot.myschedule.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/idAndTitle")
    public List<ScheduleSummaryVO> listMyScheduleIdAndTitle(Principal principal) {
        return myScheduleService.listMyScheduleIdAndTitle(principal.getName());
    }

    @GetMapping("/nextId")
    public String allocateNextMyScheduleId() {
        return myScheduleService.allocateNextMyScheduleId();
    }

    @GetMapping("/detail/{scheduleId}/title")
    public String getScheduleTitle(@PathVariable String scheduleId) {
        return myScheduleService.getScheduleTitle(scheduleId);
    }

    @GetMapping("/detail/{scheduleId}/startAt")
    public String getStartAt(@PathVariable String scheduleId) {
        return myScheduleService.getStartAt(scheduleId);
    }

    @GetMapping("/detail/{scheduleId}/route")
    public List<RouteScheduleVO> getScheduleRoute(@PathVariable String scheduleId) {
        return myScheduleService.getScheduleRoute(scheduleId);
    }

    @GetMapping("/detail/{scheduleId}/map")
    public List<MapScheduleVO> getMapSchedule(@PathVariable String scheduleId) {
        return myScheduleService.getMapSchedule(scheduleId);
    }

    @GetMapping("/detail/{scheduleId}/companion")
    public List<ColleagueVO> getCompanionList(@PathVariable String scheduleId) {
        return myScheduleService.getCompanionList(scheduleId);
    }

    @GetMapping("/detail/{scheduleId}/owned")
    public int isScheduleOwnedByUser(@PathVariable String scheduleId,
                                     Principal principal) {
        return myScheduleService.isScheduleOwnedByUser(scheduleId, principal.getName());
    }

    @PostMapping
    public boolean addMySchedule(@RequestParam String myScheduleId,
                                 @RequestParam String title,
                                 Principal principal) {
        return myScheduleService.addMySchedule(myScheduleId, title, principal.getName());
    }

    @PostMapping("/visit")
    public boolean addVisitItem(@RequestParam int visitOrder,
                                @RequestParam String placeId,
                                @RequestParam String scheduleId) {
        return myScheduleService.addVisitItem(visitOrder, placeId, scheduleId);
    }

    @PostMapping("/companion")
    public boolean addCompanion(@RequestParam String myScheduleId,
                                @RequestParam String sharedUserId) {
        return myScheduleService.addCompanion(myScheduleId, sharedUserId);
    }

    @PostMapping("/share")
    public String shareToPost(@RequestParam String myScheduleId,
                              @RequestParam int isAnonymous,
                              Principal principal) {
        return myScheduleService.shareToPost(myScheduleId, principal.getName(), isAnonymous);
    }

    @PutMapping("/title")
    public boolean setMyScheduleTitle(@RequestParam String title,
                                      @RequestParam String myScheduleId,
                                      Principal principal) {
        return myScheduleService.setMyScheduleTitle(title, myScheduleId, principal.getName());
    }

    @PutMapping("/todo")
    public boolean setTodoDetail(@RequestParam String scheduleId,
                                 @RequestParam String todoDetail) {
        return myScheduleService.setTodoDetail(scheduleId, todoDetail);
    }

    @PutMapping("/detail/{scheduleId}/budget")
    public boolean setBudgetDetail(@PathVariable String scheduleId,
                                   @RequestParam String budgetDetail) {
        return myScheduleService.setBudgetDetail(scheduleId, budgetDetail);
    }

    @PutMapping("/startAt")
    public boolean setStartAt(@RequestParam String scheduleId,
                              @RequestParam String startAt,
                              Principal principal) {
        return myScheduleService.setStartAt(scheduleId, startAt, principal.getName());
    }

    @PutMapping("/companion/permission")
    public boolean setCompanionPermission(@RequestParam String myScheduleId,
                                          @RequestParam String sharedUserId,
                                          @RequestParam String permission) {
        return myScheduleService.setCompanionPermission(myScheduleId, sharedUserId, permission);
    }

    @PutMapping("/visitOrders")
    public boolean updateVisitOrders(@RequestParam String[] visitItemIds,
                                     @RequestParam int[] visitOrders,
                                     @RequestParam String[] distances) {
        return myScheduleService.updateVisitOrders(visitItemIds, visitOrders, distances);
    }

    @PutMapping
    public boolean setMySchedule(@RequestParam String[] visitItemId,
                                 @RequestParam int[] visitOrder,
                                 @RequestParam String[] distanceToNext,
                                 @RequestParam String scheduleId,
                                 @RequestParam String scheduleTitle,
                                 @RequestParam String startAt,
                                 @RequestParam String budgetDetail,
                                 @RequestParam String todoDetail,
                                 @RequestParam int isShared,
                                 Principal principal) {
        return myScheduleService.setMySchedule(visitItemId, visitOrder, distanceToNext,
                scheduleId, scheduleTitle, startAt, budgetDetail, todoDetail, principal.getName(), isShared);
    }

    @DeleteMapping("/list")
    public boolean deleteMyScheduleList(@RequestParam String[] scheduleIds,
                                        Principal principal) {
        return myScheduleService.deleteMyScheduleList(principal.getName(), scheduleIds);
    }

    @DeleteMapping("/visit/{visitItemId}")
    public boolean deleteVisitItemById(@PathVariable String visitItemId) {
        boolean bool = myScheduleService.deleteVisitItemById(visitItemId);
        log.info("결과", bool);
        return bool;
    }

    @DeleteMapping("/{scheduleId}")
    public boolean deleteMySchedule(@PathVariable String scheduleId) {
        return myScheduleService.deleteMySchedule(scheduleId);
    }
}
