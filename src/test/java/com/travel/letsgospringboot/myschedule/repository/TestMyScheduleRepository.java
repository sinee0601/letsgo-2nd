package com.travel.letsgospringboot.myschedule.repository;

import com.travel.letsgospringboot.myschedule.vo.ColleagueVO;
import com.travel.letsgospringboot.myschedule.vo.MapScheduleVO;
import com.travel.letsgospringboot.myschedule.vo.MyScheduleVO;
import com.travel.letsgospringboot.myschedule.vo.RouteScheduleVO;
import com.travel.letsgospringboot.myschedule.vo.ScheduleSummaryVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// 변경 테스트가 공유 원격 DB를 오염시키지 않도록 각 테스트 종료 시 롤백한다.
@SpringBootTest
@Transactional
public class TestMyScheduleRepository {

    @Autowired
    MyScheduleRepository myScheduleRepository;

    // DummyData.sql 기준
    private static final String USER = "user01";
    private static final String OWNED_SCHEDULE = "S002";   // user01 소유, 비공개
    private static final String SHARED_SCHEDULE = "S001";  // user01 소유, 공개(방문지·동행자 보유)

    // ---- 조회: 전체 (날짜순) ----
    @Test
    void testGetMyScheduleListAllByDate() {
        System.out.println(myScheduleRepository.getMyScheduleListAllByDate(USER));
    }

    @Test
    void getMyScheduleListAllByDate_existingUser_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListAllByDate(USER));
    }

    @Test
    void getMyScheduleListAllByDate_existingUser_returnsItems() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByDate(USER);
        assertFalse(result.isEmpty(), "user01은 일정이 존재해야 합니다");
    }

    @Test
    void getMyScheduleListAllByDate_voFieldsNotNull() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByDate(USER);
        MyScheduleVO first = result.get(0);
        assertNotNull(first.getMyScheduleId(), "myScheduleId가 null이면 안 됩니다");
        assertNotNull(first.getMyScheduleTitle(), "myScheduleTitle이 null이면 안 됩니다");
    }

    @Test
    void getMyScheduleListAllByDate_nonexistentUser_returnsEmptyList() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByDate("noSuchUser9999");
        assertNotNull(result);
        assertTrue(result.isEmpty(), "존재하지 않는 사용자는 빈 목록이어야 합니다");
    }

    // ---- 조회: 전체 (제목순) ----
    @Test
    void getMyScheduleListAllByTitle_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListAllByTitle(USER));
    }

    @Test
    void getMyScheduleListAllByTitle_nonexistentUser_returnsEmptyList() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByTitle("noSuchUser9999");
        assertNotNull(result);
        assertTrue(result.isEmpty(), "존재하지 않는 사용자는 빈 목록이어야 합니다");
    }

    // ---- 조회: 공유 ----
    @Test
    void getMyScheduleListSharedByDate_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListSharedByDate(USER));
    }

    @Test
    void getMyScheduleListSharedByDate_returnsItems() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListSharedByDate(USER);
        assertFalse(result.isEmpty(), "user01의 공유 일정이 존재해야 합니다");
    }

    @Test
    void getMyScheduleListSharedByTitle_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListSharedByTitle(USER));
    }

    // ---- 검색 ----
    @Test
    void getMyScheduleListSearchByDate_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListSearchByDate(USER, "서대문"));
        assertNotNull(myScheduleRepository.getMyScheduleListSearchByDate(USER, ""));
    }

    @Test
    void getMyScheduleListSearchByDate_keywordMatch_returnsResults() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListSearchByDate(USER, "서대문");
        assertNotNull(result);
        assertFalse(result.isEmpty(), "'서대문' 검색 시 결과가 있어야 합니다");
    }

    @Test
    void getMyScheduleListSearchByTitle_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListSearchByTitle(USER, "서대문"));
        assertNotNull(myScheduleRepository.getMyScheduleListSearchByTitle(USER, ""));
    }

    @Test
    void getMyScheduleListSearchByTitle_noMatch_returnsEmptyList() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListSearchByTitle(USER, "절대매칭안되는키워드xyzxyz");
        assertNotNull(result);
        assertTrue(result.isEmpty(), "매칭되지 않는 키워드는 빈 목록이어야 합니다");
    }

    @Test
    void getMyScheduleListSearchSharedByDate_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListSearchSharedByDate(USER, "서대문"));
        assertNotNull(myScheduleRepository.getMyScheduleListSearchSharedByDate(USER, ""));
    }

    @Test
    void getMyScheduleListSearchSharedByTitle_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListSearchSharedByTitle(USER, "서대문"));
        assertNotNull(myScheduleRepository.getMyScheduleListSearchSharedByTitle(USER, ""));
    }

    @Test
    void getMyScheduleList_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleList(USER, "서대문", "title", false));
        assertNotNull(myScheduleRepository.getMyScheduleList(USER, "", "title", false));
        assertNotNull(myScheduleRepository.getMyScheduleList(USER, null, "date", true));
    }

    // ---- 단건 조회 ----
    @Test
    void getScheduleTitle_returnsNonNull() {
        assertNotNull(myScheduleRepository.getScheduleTitle(OWNED_SCHEDULE));
    }

    @Test
    void getStartAt_returnsNonNull() {
        assertNotNull(myScheduleRepository.getStartAt(OWNED_SCHEDULE));
    }

    // ---- 수정 (set 후 get으로 검증) ----
    @Test
    void setAndGetTodoDetail() {
        assertTrue(myScheduleRepository.setTodoDetail(OWNED_SCHEDULE, "햄부기 사냥함부기"));
        assertEquals("햄부기 사냥함부기", myScheduleRepository.getTodoDetail(OWNED_SCHEDULE));
    }

    @Test
    void setAndGetBudgetDetail() {
        assertTrue(myScheduleRepository.setBudgetDetail(OWNED_SCHEDULE, "1인당 2만원"));
        assertEquals("1인당 2만원", myScheduleRepository.getBudgetDetail(OWNED_SCHEDULE));
    }

    @Test
    void setStartAt_ownedSchedule_returnsTrue() {
        assertTrue(myScheduleRepository.setStartAt(OWNED_SCHEDULE, "2026-05-20", USER));
    }

    @Test
    void setMyScheduleTitle_ownedSchedule_returnsTrue() {
        assertTrue(myScheduleRepository.setMyScheduleTitle("새 제목 테스트", OWNED_SCHEDULE, USER));
    }

    // ---- 지도용 방문지 조회 ----
    @Test
    void getMapSchedule_returnsItems() {
        List<MapScheduleVO> list = myScheduleRepository.getMapSchedule(SHARED_SCHEDULE);
        assertNotNull(list);
        assertFalse(list.isEmpty(), "S001은 방문지가 있어야 합니다");
        assertNotNull(list.get(0).getTitle());
    }

    // ---- 방문지 추가/삭제 ----
    @Test
    void addVisitItem_returnsTrue() {
        assertTrue(myScheduleRepository.addVisitItem(5, "1", OWNED_SCHEDULE));
    }

    @Test
    void deleteVisitItemById_nonexistent_returnsFalse() {
        assertFalse(myScheduleRepository.deleteVisitItemById("nonexistent-visit-item"));
    }

    // ---- 동행자 ----
    @Test
    void addCompanion_returnsTrue() {
        assertTrue(myScheduleRepository.addCompanion(OWNED_SCHEDULE, "user04"));
    }

    @Test
    void setCompanionPermission_existingShare_returnsTrue() {
        assertTrue(myScheduleRepository.setCompanionPermission(SHARED_SCHEDULE, "user02", "W"));
    }

    @Test
    void getCompanionList_hasEntries() {
        List<ColleagueVO> list = myScheduleRepository.getCompanionList(SHARED_SCHEDULE);
        assertNotNull(list);
        assertFalse(list.isEmpty(), "S001에 동행자가 존재해야 합니다");
    }

    @Test
    void getCompanionList_noShare_returnsEmptyList() {
        List<ColleagueVO> list = myScheduleRepository.getCompanionList("S999");
        assertNotNull(list);
        assertTrue(list.isEmpty(), "공유자가 없는 일정은 빈 목록이어야 합니다");
    }

    // ---- 일정 행 삽입 ----
    @Test
    void insertMyScheduleRow_returnsTrue() {
        assertTrue(myScheduleRepository.insertMyScheduleRow("S999", "테스트 일정", USER));
    }

    @Test
    void isScheduleOwnedByUser() {
        assertTrue(myScheduleRepository.isScheduleOwnedByUser(OWNED_SCHEDULE, USER));
        assertFalse(myScheduleRepository.isScheduleOwnedByUser(OWNED_SCHEDULE, "user99"));
    }

    @Test
    void listMyScheduleIdAndTitle() {
        List<ScheduleSummaryVO> list = myScheduleRepository.listMyScheduleIdAndTitle(USER);
        assertNotNull(list);
        assertFalse(list.isEmpty(), "user01은 소유 일정이 있어야 합니다");
        assertNotNull(list.get(0).getMyScheduleId());
        assertNotNull(list.get(0).getTitle());
    }

    @Test
    void getScheduleRoute_returnsNonNull() {
        List<RouteScheduleVO> list = myScheduleRepository.getScheduleRoute(SHARED_SCHEDULE);
        assertNotNull(list);
    }

    // 아래 6개는 매퍼에 대응 statement가 없어(조합 로직 미구현) 호출 시 실패한다.
    // deleteMySchedule, deleteMyScheduleList, setMySchedule, updateVisitOrders,
    // allocateNextMyScheduleId, shareToPost
    @Test
    void allocateNextMyScheduleId_startsWithS() {
        String id = myScheduleRepository.allocateNextMyScheduleId();
        assertNotNull(id);
        assertTrue(id.startsWith("S"), "ID는 S로 시작해야 합니다");
    }

    @Test
    void deleteMySchedule_returnsTrue() {
        assertTrue(myScheduleRepository.deleteMySchedule(OWNED_SCHEDULE));
    }

    @Test
    void deleteMyScheduleList_returnsTrue() {
        String[] ids = { "S001", "S002" };
        assertTrue(myScheduleRepository.deleteMyScheduleList(USER, ids));
    }

    @Test
    void setMySchedule_returnsTrue() {
        String[] visitItemIds = { "4", "5", "6" };
        int[] visitOrders = { 3, 2, 1 };
        String[] distanceToNexts = { "64", "23", "35" };
        assertTrue(myScheduleRepository.setMySchedule(visitItemIds, visitOrders, distanceToNexts,
                OWNED_SCHEDULE, "여의도 대탐방", "2026-05-15",
                "삼겹살 마구 먹기", "햄부기 사냥하기", USER, 1));
    }

    @Test
    void updateVisitOrders_returnsTrue() {
        String[] visitItemIds = { "4", "5", "6" };
        int[] visitOrders = { 1, 2, 3 };
        String[] distances = { "100", "200", "0" };
        assertTrue(myScheduleRepository.updateVisitOrders(visitItemIds, visitOrders, distances));
    }

    @Test
    void shareToPost_returnsPostId() {
        String result = myScheduleRepository.shareToPost(OWNED_SCHEDULE, USER, 0);
        assertNotNull(result);
    }
}
