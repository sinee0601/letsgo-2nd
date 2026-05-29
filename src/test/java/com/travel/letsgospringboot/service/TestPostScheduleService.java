package com.travel.letsgospringboot.service;

import com.travel.letsgospringboot.postschedule.service.PostScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class TestPostScheduleService {

    @Autowired
    PostScheduleService postScheduleService;

    @Test
    public void testDeletePostSchedule() {
        postScheduleService.deletePostSchedule("P001");
    }


    @Test
    public void testAddToMySchedule() {
        postScheduleService.addToMySchedule("P018", "user01");
    }

}
