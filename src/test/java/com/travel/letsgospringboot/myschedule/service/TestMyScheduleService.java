package com.travel.letsgospringboot.myschedule.service;

import com.travel.letsgospringboot.myschedule.vo.VisitOrderVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// 변경 테스트가 공유 원격 DB를 오염시키지 않도록 각 테스트 종료 시 롤백한다.
@SpringBootTest
@Transactional
public class TestMyScheduleService {

    @Autowired
    MyScheduleService myScheduleService;

    private static final String USER = "user01";
    private static final String OWNED_SCHEDULE = "S002"; // user01 소유, 비공개 (방문지 visit_item_id 4·5·6)

    @Test
    void deleteMySchedule_returnsTrue() {
        assertTrue(myScheduleService.deleteMySchedule(OWNED_SCHEDULE, USER));
    }

    @Test
    void deleteMyScheduleList_returnsTrue() {
        String[] ids = { "S001", "S002" };
        assertTrue(myScheduleService.deleteMyScheduleList(USER, ids));
    }

    @Test
    void setMySchedule_returnsTrue() {
        String[] visitItemIds = { "4", "5", "6" };
        int[] visitOrders = { 3, 2, 1 };
        String[] distanceToNexts = { "64", "23", "35" };
        assertTrue(myScheduleService.setMySchedule(visitItemIds, visitOrders, distanceToNexts,
                OWNED_SCHEDULE, "여의도 대탐방", "2026-05-15",
                "삼겹살 마구 먹기", "햄부기 사냥하기", USER, 1));
    }

//    @Test
//    void updateVisitOrders_returnsTrue() {
//        List<VisitOrderVO> orders = List.of(
//                VisitOrderVO.builder().visitItemId("4").visitOrder(1).distance("100").build(),
//                VisitOrderVO.builder().visitItemId("5").visitOrder(2).distance("200").build(),
//                VisitOrderVO.builder().visitItemId("6").visitOrder(3).distance("0").build()
//        );
//        assertTrue(myScheduleService.updateVisitOrders(orders));
//    }

    @Test
    void allocateNextMyScheduleId_startsWithS() {
        String id = myScheduleService.allocateNextMyScheduleId();
        assertNotNull(id);
        assertTrue(id.startsWith("S"), "ID는 S로 시작해야 합니다");
    }

    @Test
    void shareToPost_returnsPostId() {
        String result = myScheduleService.shareToPost(OWNED_SCHEDULE, USER, 0);
        assertNotNull(result);
        assertTrue(result.startsWith("P"), "게시물 ID는 P로 시작해야 합니다");
    }
}
