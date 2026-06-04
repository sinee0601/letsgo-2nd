package com.travel.letsgospringboot.myschedule.controller;

import com.travel.letsgospringboot.myschedule.service.MyScheduleService;
import com.travel.letsgospringboot.myschedule.vo.ColleagueVO;
import com.travel.letsgospringboot.myschedule.vo.MapScheduleVO;
import com.travel.letsgospringboot.myschedule.vo.RouteScheduleVO;
import com.travel.letsgospringboot.myschedule.vo.ScheduleSummaryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/myschedule")
@RequiredArgsConstructor
public class MyScheduleController {

    private final MyScheduleService myScheduleService;

    @GetMapping("/list")
    @ResponseBody
    public Collection getMyScheduleList(@RequestParam(name = "userId", required = true) String userId,
                                        @RequestBody SearchRequest searchRequest) {
        boolean isSortTitle = searchRequest.getSortType().equals("title");
        boolean hasKeyword = !searchRequest.getSearchTitle().isEmpty();
        if (searchRequest.isShared()) {
            if (hasKeyword) {
                return isSortTitle
                        ? myScheduleService.getMyScheduleListSearchSharedByTitle(userId, searchRequest.getSearchTitle())
                        : myScheduleService.getMyScheduleListSearchSharedByDate(userId, searchRequest.getSearchTitle());
            }
            return isSortTitle
                    ? myScheduleService.getMyScheduleListSharedByTitle(userId)
                    : myScheduleService.getMyScheduleListSharedByDate(userId);
        }
        if (hasKeyword) {
            return isSortTitle
                    ? myScheduleService.getMyScheduleListSearchByTitle(userId, searchRequest.getSearchTitle())
                    : myScheduleService.getMyScheduleListSearchByDate(userId, searchRequest.getSearchTitle());
        }
        return isSortTitle
                ? myScheduleService.getMyScheduleListAllByTitle(userId)
                : myScheduleService.getMyScheduleListAllByDate(userId);
    }

    @GetMapping("/idAndTitle")
    @ResponseBody
    public List<ScheduleSummaryVO> listMyScheduleIdAndTitle(@RequestParam String userId) {
        return myScheduleService.listMyScheduleIdAndTitle(userId);
    }

    @GetMapping("/nextId")
    @ResponseBody
    public String allocateNextMyScheduleId() {
        return myScheduleService.allocateNextMyScheduleId();
    }

    @GetMapping("/detail/{scheduleId}/title")
    @ResponseBody
    public String getScheduleTitle(@PathVariable String scheduleId) {
        return myScheduleService.getScheduleTitle(scheduleId);
    }

    @GetMapping("/detail/{scheduleId}/todo")
    @ResponseBody
    public String getTodoDetail(@PathVariable String scheduleId) {
        return myScheduleService.getTodoDetail(scheduleId);
    }

    @GetMapping("/detail/{scheduleId}/budget")
    @ResponseBody
    public String getBudgetDetail(@PathVariable String scheduleId) {
        return myScheduleService.getBudgetDetail(scheduleId);
    }

    @GetMapping("/detail/{scheduleId}/startAt")
    @ResponseBody
    public String getStartAt(@PathVariable String scheduleId) {
        return myScheduleService.getStartAt(scheduleId);
    }

    @GetMapping("/detail/{scheduleId}/route")
    @ResponseBody
    public List<RouteScheduleVO> getScheduleRoute(@PathVariable String scheduleId) {
        return myScheduleService.getScheduleRoute(scheduleId);
    }

    @GetMapping("/detail/{scheduleId}/map")
    @ResponseBody
    public List<MapScheduleVO> getMapSchedule(@PathVariable String scheduleId) {
        return myScheduleService.getMapSchedule(scheduleId);
    }

    @GetMapping("/detail/{scheduleId}/companion")
    @ResponseBody
    public List<ColleagueVO> getCompanionList(@PathVariable String scheduleId) {
        return myScheduleService.getCompanionList(scheduleId);
    }

    @GetMapping("/detail/{scheduleId}/owned")
    @ResponseBody
    public int isScheduleOwnedByUser(@PathVariable String scheduleId,
                                     @RequestParam String userId) {
        return myScheduleService.isScheduleOwnedByUser(scheduleId, userId);
    }

    @PostMapping
    @ResponseBody
    public boolean addMySchedule(@RequestParam String myScheduleId,
                                       @RequestParam String title,
                                       @RequestParam String userId) {
        return myScheduleService.addMySchedule(myScheduleId, title, userId);
    }

    @PostMapping("/visit")
    @ResponseBody
    public boolean addVisitItem(@RequestParam int visitOrder,
                                @RequestParam String placeId,
                                @RequestParam String scheduleId) {
        return myScheduleService.addVisitItem(visitOrder, placeId, scheduleId);
    }

    @PostMapping("/companion")
    @ResponseBody
    public boolean addCompanion(@RequestParam String myScheduleId,
                                @RequestParam String sharedUserId) {
        return myScheduleService.addCompanion(myScheduleId, sharedUserId);
    }

    @PostMapping("/share")
    @ResponseBody
    public String shareToPost(@RequestParam String myScheduleId,
                              @RequestParam String userId,
                              @RequestParam int isAnonymous) {
        return myScheduleService.shareToPost(myScheduleId, userId, isAnonymous);
    }

    @PutMapping("/title")
    @ResponseBody
    public boolean setMyScheduleTitle(@RequestParam String title,
                                      @RequestParam String myScheduleId,
                                      @RequestParam String userId) {
        return myScheduleService.setMyScheduleTitle(title, myScheduleId, userId);
    }

    @PutMapping("/todo")
    @ResponseBody
    public boolean setTodoDetail(@RequestParam String scheduleId,
                                 @RequestParam String todoDetail) {
        return myScheduleService.setTodoDetail(scheduleId, todoDetail);
    }

    @PutMapping("/budget")
    @ResponseBody
    public boolean setBudgetDetail(@RequestParam String scheduleId,
                                   @RequestParam String budgetDetail) {
        return myScheduleService.setBudgetDetail(scheduleId, budgetDetail);
    }

    @PutMapping("/startAt")
    @ResponseBody
    public boolean setStartAt(@RequestParam String scheduleId,
                              @RequestParam String startAt,
                              @RequestParam String userId) {
        return myScheduleService.setStartAt(scheduleId, startAt, userId);
    }

    @PutMapping("/companion/permission")
    @ResponseBody
    public boolean setCompanionPermission(@RequestParam String myScheduleId,
                                          @RequestParam String sharedUserId,
                                          @RequestParam String permission) {
        return myScheduleService.setCompanionPermission(myScheduleId, sharedUserId, permission);
    }

    @PutMapping("/visitOrders")
    @ResponseBody
    public boolean updateVisitOrders(@RequestParam String[] visitItemIds,
                                     @RequestParam int[] visitOrders,
                                     @RequestParam String[] distances) {
        return myScheduleService.updateVisitOrders(visitItemIds, visitOrders, distances);
    }

    @PutMapping
    @ResponseBody
    public boolean setMySchedule(@RequestParam String[] visitItemId,
                                 @RequestParam int[] visitOrder,
                                 @RequestParam String[] distanceToNext,
                                 @RequestParam String scheduleId,
                                 @RequestParam String scheduleTitle,
                                 @RequestParam String startAt,
                                 @RequestParam String budgetDetail,
                                 @RequestParam String todoDetail,
                                 @RequestParam String userId,
                                 @RequestParam int isShared) {
        return myScheduleService.setMySchedule(visitItemId, visitOrder, distanceToNext,
                scheduleId, scheduleTitle, startAt, budgetDetail, todoDetail, userId, isShared);
    }

    @DeleteMapping("/{scheduleId}")
    @ResponseBody
    public boolean deleteMySchedule(@PathVariable String scheduleId) {
        return myScheduleService.deleteMySchedule(scheduleId);
    }

    @DeleteMapping("/list")
    @ResponseBody
    public boolean deleteMyScheduleList(@RequestParam String userId,
                                        @RequestParam String[] scheduleIds) {
        return myScheduleService.deleteMyScheduleList(userId, scheduleIds);
    }

    @DeleteMapping("/visit/{visitItemId}")
    @ResponseBody
    public boolean deleteVisitItemById(@PathVariable String visitItemId) {
        return myScheduleService.deleteVisitItemById(visitItemId);
    }
}
