package com.travel.letsgospringboot.myschedule.controller;

import com.travel.letsgospringboot.myschedule.controller.request.*;
import com.travel.letsgospringboot.myschedule.service.MyScheduleService;
import com.travel.letsgospringboot.myschedule.vo.*;
import com.travel.letsgospringboot.user.auth.AppUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/myschedule/api")
@RequiredArgsConstructor
public class MyScheduleRestController {

    private final MyScheduleService myScheduleService;

    @GetMapping("/list")
    public List<MyScheduleVO> getMyScheduleList(@ModelAttribute SearchRequest searchRequest,
                                                @AuthenticationPrincipal AppUserDetails userDetails) {

        String userId = userDetails.getUsername();
        String searchTitle = searchRequest.getSearchTitle() == null ? "" : searchRequest.getSearchTitle();
        boolean isShared = searchRequest.isShared();
        boolean isSortTitle = "title".equals(searchRequest.getSortType());
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
    public boolean addMySchedule(@RequestBody AddScheduleRequest addScheduleRequest,
                                 @AuthenticationPrincipal AppUserDetails userDetails) {
        return myScheduleService.addMySchedule(ScheduleCreateVO.builder()
                .myScheduleId(addScheduleRequest.getScheduleId())
                .title(addScheduleRequest.getTitle())
                .userId(userDetails.getUsername())
                .build());
    }

    @DeleteMapping("/{scheduleId}")
    public boolean deleteMySchedule(@PathVariable String scheduleId,
                                    @AuthenticationPrincipal AppUserDetails userDetails) {
        return myScheduleService.deleteMySchedule(scheduleId, userDetails.getUsername());
    }

    @PutMapping("/{scheduleId}/title")
    public boolean setMyScheduleTitle(@PathVariable String scheduleId,
                                      @RequestBody ScheduleTitleRequest scheduleTitleRequest,
                                      @AuthenticationPrincipal AppUserDetails userDetails) {
        return myScheduleService.setMyScheduleTitle(ScheduleTitleUpdateVO.builder()
                .title(scheduleTitleRequest.getTitle())
                .scheduleId(scheduleId)
                .userId(userDetails.getUsername())
                .build());
    }

    @PutMapping("/{scheduleId}/startAt")
    public boolean setStartAt(@PathVariable String scheduleId,
                              @RequestBody StartAtRequest startAtRequest,
                              @AuthenticationPrincipal AppUserDetails userDetails) {
        return myScheduleService.setStartAt(StartAtUpdateVO.builder()
                .scheduleId(scheduleId)
                .startAt(startAtRequest.getStartAt())
                .userId(userDetails.getUsername())
                .build());
    }

    @PutMapping("/{scheduleId}/todo")
    public boolean setTodoDetail(@PathVariable String scheduleId,
                                 @RequestBody TodoDetailRequest todoDetailRequest,
                                 @AuthenticationPrincipal AppUserDetails userDetails) {
        return myScheduleService.setTodoDetail(TodoUpdateVO.builder()
                .scheduleId(scheduleId)
                .todoDetail(todoDetailRequest.getTodoDetail())
                .userId(userDetails.getUsername())
                .build());
    }

    @PutMapping("/{scheduleId}/budget")
    public boolean setBudgetDetail(@PathVariable String scheduleId,
                                   @RequestBody BudgetDetailRequest budgetDetailRequest,
                                   @AuthenticationPrincipal AppUserDetails userDetails) {
        return myScheduleService.setBudgetDetail(BudgetUpdateVO.builder()
                .scheduleId(scheduleId)
                .budgetDetail(budgetDetailRequest.getBudgetDetail())
                .userId(userDetails.getUsername())
                .build());
    }

    @PostMapping("/{scheduleId}/share")
    public String shareToPost(@PathVariable String scheduleId,
                              @RequestBody ShareRequest shareRequest,
                              @AuthenticationPrincipal AppUserDetails userDetails) {
        return myScheduleService.shareToPost(ShareVO.builder()
                .scheduleId(scheduleId)
                .userId(userDetails.getUsername())
                .isAnonymous(shareRequest.getIsAnonymous())
                .build());
    }

    @GetMapping("/{scheduleId}/companion")
    public List<ColleagueVO> getCompanionList(@PathVariable String scheduleId,
                                              @AuthenticationPrincipal AppUserDetails userDetails) {
        return myScheduleService.getCompanionList(scheduleId, userDetails.getUsername());
    }

    @PostMapping("/{scheduleId}/companion")
    public boolean addCompanion(@PathVariable String scheduleId,
                                @RequestBody CompanionRequest companionRequest) {
        return myScheduleService.addCompanion(scheduleId, companionRequest.getSharedUserId());
    }

    @PutMapping("/{scheduleId}/companion/permission")
    public boolean setCompanionPermission(@PathVariable String scheduleId,
                                          @RequestBody CompanionPermissionRequest companionPermissionRequest,
                                          @AuthenticationPrincipal AppUserDetails userDetails) {
        return myScheduleService.setCompanionPermission(CompanionPermissionVO.builder()
                .scheduleId(scheduleId)
                .sharedUserId(companionPermissionRequest.getSharedUserId())
                .permission(companionPermissionRequest.getPermission())
                .userId(userDetails.getUsername())
                .build());
    }

    @PostMapping("/{scheduleId}/visit")
    public boolean addVisitItem(@PathVariable String scheduleId,
                                @RequestBody VisitItemRequest visitItemRequest,
                                @AuthenticationPrincipal AppUserDetails userDetails) {
        return myScheduleService.addVisitItem(VisitItemCreateVO.builder()
                .visitOrder(visitItemRequest.getVisitOrder())
                .placeId(visitItemRequest.getPlaceId())
                .scheduleId(scheduleId)
                .userId(userDetails.getUsername())
                .build());
    }

    @PutMapping("/{scheduleId}/visit/orders")
    public boolean updateVisitOrders(@PathVariable String scheduleId,
                                     @RequestBody List<VisitOrderVO> visitOrderVOS,
                                     @AuthenticationPrincipal AppUserDetails userDetails) {
        return myScheduleService.updateVisitOrders(VisitOrderUpdateVO.builder()
                .orders(visitOrderVOS)
                .scheduleId(scheduleId)
                .userId(userDetails.getUsername())
                .build());
    }

    @DeleteMapping("/{scheduleId}/visit/{visitItemId}")
    public boolean deleteVisitItemById(@PathVariable String scheduleId,
                                       @PathVariable String visitItemId,
                                       @AuthenticationPrincipal AppUserDetails userDetails) {
        return myScheduleService.deleteVisitItemById(VisitItemDeleteVO.builder()
                .visitItemId(visitItemId)
                .scheduleId(scheduleId)
                .userId(userDetails.getUsername())
                .build());
    }

    @GetMapping("/{scheduleId}/mapSchedule")
    public List<MapScheduleVO> getMapSchedule(@PathVariable String scheduleId,
                                              @AuthenticationPrincipal AppUserDetails userDetails){
        return myScheduleService.getMapSchedule(scheduleId, userDetails.getUsername());
    }

}
