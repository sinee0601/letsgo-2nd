package com.travel.letsgospringboot.myschedule.controller;

import com.travel.letsgospringboot.myschedule.service.MyScheduleService;
import com.travel.letsgospringboot.myschedule.vo.MyScheduleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/myschedule/api")
@RequiredArgsConstructor
public class MyScheduleRestController {

    private final MyScheduleService myScheduleService;

    @GetMapping("/list")
    public List<MyScheduleVO> getMyScheduleList(
            @RequestParam String userId,
            @RequestParam(defaultValue = "") String searchTitle,
            @RequestParam(defaultValue = "date") String sortOrder,
            @RequestParam(defaultValue = "false") boolean isShared) {

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
}
