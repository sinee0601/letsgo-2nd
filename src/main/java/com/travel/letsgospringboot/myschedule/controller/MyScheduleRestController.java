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
    public List<MyScheduleVO> getMyScheduleList(@RequestParam(required = true) String userId, @RequestParam(required = false) String sortType
    ,@RequestParam boolean isShared, @RequestParam(required = false) String keyword ) {
        List<MyScheduleVO> list;

        if (sortType.equals("title")) {
            if (isShared) {
                return myScheduleService.getMyScheduleListSearchSharedByTitle(userId, keyword);
            } else
                list = myScheduleService.getMyScheduleListSearchByTitle(userId, keyword);
        } else {
            if (isShared) {
                return myScheduleService.getMyScheduleListSearchSharedByDate(userId, keyword);
            } else
                list = myScheduleService.getMyScheduleListSearchByDate(userId, keyword);

        }
        return list;
    }
}
