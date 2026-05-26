package com.travel.letsgospringboot.myschedule.repository;

import com.travel.letsgospringboot.myschedule.vo.ColleagueVO;
import com.travel.letsgospringboot.myschedule.vo.MapScheduleVO;
import com.travel.letsgospringboot.myschedule.vo.MyScheduleVO;
import com.travel.letsgospringboot.myschedule.vo.RouteScheduleVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class TestMyScheduleRepository {

    @Autowired
    MyScheduleRepository myScheduleRepository;

    @Test
    void testGetMyScheduleListAllByDate() {
        System.out.println(myScheduleRepository.getMyScheduleListAllByDate("user01"));
    }

    @Test
    public void getMyScheduleListAllByDate_existingUser_returnsNonNull() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByDate("user01");
        assertNotNull(result);
    }

    @Test
    public void getMyScheduleListAllByDate_existingUser_returnsItems() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByDate("user01");
        assertFalse("user01", result.isEmpty());
    }

    @Test
    public void getMyScheduleListAllByDate_voFieldsNotNull() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByDate("user01");
        MyScheduleVO first = result.get(0);
        assertNotNull("myScheduleId가 null이면 안 됩니다", first.getMyScheduleId());
        assertNotNull("myScheduleTitle이 null이면 안 됩니다", first.getMyScheduleTitle());
    }

    @Test
    public void getMyScheduleListAllByDate_nonexistentUser_returnsEmptyList() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByDate("noSuchUser9999");
        assertNotNull(result);
        assertTrue("존재하지 않는 사용자는 빈 목록이어야 합니다", result.isEmpty());
    }

    @Test
    public void getMyScheduleListAllByTitleTest() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByTitle("user01");
        assertNotNull(result);
    }

    @Test
    public void getMyScheduleListAllByTitle_nonexistentUser_returnsEmptyList() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByTitle("noSuchUser9999");
        assertNotNull(result);
        assertTrue("존재하지 않는 사용자는 빈 목록이어야 합니다", result.isEmpty());
    }

    @Test
    public void getMyScheduleListSharedByDateTest() {
        assertNotNull(myScheduleRepository.getMyScheduleListSharedByDate("user01"));
    }

    @Test
    public void getMyScheduleListSharedByDate_returnsItems() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListSharedByDate("user01");
        assertFalse("user01의 공유 일정이 존재해야 합니다", result.isEmpty());
    }

    @Test
    public void getMyScheduleListSharedByTitleTest() {
        assertNotNull(myScheduleRepository.getMyScheduleListSharedByTitle("user01"));
    }

    @Test
    public void getMyScheduleListSearchByDateTest() {
        assertNotNull(myScheduleRepository.getMyScheduleListSearchByDate("user01", "서대문"));
        assertNotNull(myScheduleRepository.getMyScheduleListSearchByDate("user01", ""));
    }

    @Test
    public void getMyScheduleListSearchByDate_keywordMatch_returnsResults() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListSearchByDate("user01", "서대문");
        assertNotNull(result);
        assertTrue("햄부기부기",result.isEmpty());
    }

    @Test
    public void getMyScheduleListSearchByTitleTest() {
        assertNotNull(myScheduleRepository.getMyScheduleListSearchByTitle("user01", "서대문"));
        assertNotNull(myScheduleRepository.getMyScheduleListSearchByTitle("user01", ""));
    }

    @Test
    public void getMyScheduleListSearchByTitle_noMatch_returnsEmptyList() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListSearchByTitle("user01", "절대매칭안되는키워드xyzxyz");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getMyScheduleListSearchSharedByDateTest() {
        assertNotNull(myScheduleRepository.getMyScheduleListSearchSharedByDate("user01", "서대문"));
        assertNotNull(myScheduleRepository.getMyScheduleListSearchSharedByDate("user01", ""));
    }

    @Test
    public void getMyScheduleListSearchSharedByTitleTest() {
        assertNotNull(myScheduleRepository.getMyScheduleListSearchSharedByTitle("user01", "서대문"));
        assertNotNull(myScheduleRepository.getMyScheduleListSearchSharedByTitle("user01", ""));
    }

    @Test
    public void getMyScheduleListTest() {
        assertNotNull(myScheduleRepository.getMyScheduleList("user01", "서대문", "title", false));
        assertNotNull(myScheduleRepository.getMyScheduleList("user01", "", "title", false));
        assertNotNull(myScheduleRepository.getMyScheduleList("user01", null, "date", true));
    }

    @Test
    public void deleteMyScheduleTest() {
        assertTrue(myScheduleRepository.deleteMySchedule("SCH092"));
    }

    @Test
    public void deleteMyScheduleListTest() {
        String[] ids = { "SCH072", "SCH092" };
        assertTrue(myScheduleRepository.deleteMyScheduleList("mskk0410", ids));
    }

    @Test
    public void setMyScheduleTest() {
        String[] visitItemIds = { "4", "5", "6" };
        int[] visitOrders = { 3, 2, 1 };
        String[] distanceToNexts = { "64", "23", "35" };
        assertTrue(myScheduleRepository.setMySchedule(visitItemIds, visitOrders, distanceToNexts,
                "SCH092", "여의도 대탐방", "26/05/15",
                "삼겹살 마구 먹기", "햄부기 사냥하기", "mskk0410", 1));
    }

    @Test
    public void setAndGetTodoDetailTest() {
        assertTrue(myScheduleRepository.setTodoDetail("SCH092", "햄부기 사냥함부기"));
        assertEquals("햄부기 사냥함부기", dao.getTodoDetail("SCH092"));
    }

    @Test
    public void setAndGetBudgetDetailTest() {
        assertTrue(dao.setBudgetDetail("SCH092", "1인당 2만원"));
        assertEquals("1인당 2만원", myScheduleRepository.getBudgetDetail("SCH092"));
    }

    @Test
    public void getScheduleTitleTest() {
        assertNotNull(myScheduleRepository.getScheduleTitle("SCH092"));
    }

    @Test
    public void getStartAtTest() {
        assertNotNull(myScheduleRepository.getStartAt("SCH092"));
    }

    @Test
    public void setStartAtTest() {
        assertTrue(myScheduleRepository.setStartAt("SCH092", "2026-05-15", "mskk0410"));
    }

    @Test
    public void setMyScheduleTitleTest() {
        assertTrue(myScheduleRepository.setMyScheduleTitle("새 제목 테스트", "SCH092", "mskk0410"));
    }

    @Test
    public void isScheduleOwnedByUserTest() {
        assertTrue(myScheduleRepository.isScheduleOwnedByUser("SCH092", "mskk0410"));
        assertFalse(myScheduleRepository.isScheduleOwnedByUser("SCH001", "user99"));
    }

    @Test
    public void listMyScheduleIdAndTitleTest() {
        List<String[]> list = myScheduleRepository.listMyScheduleIdAndTitle("user01");
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertNotNull(list.get(0)[0]);
        assertNotNull(list.get(0)[1]);
    }

    @Test
    public void updateVisitOrdersTest() {
        String[] visitItemIds = { "4", "5", "6" };
        int[] visitOrders = { 1, 2, 3 };
        String[] distances = { "100", "200", "0" };
        assertTrue(myScheduleRepository.updateVisitOrders(visitItemIds, visitOrders, distances));
    }

    @Test
    public void getScheduleRouteTest() {
        List<RouteScheduleVO> list = myScheduleRepository.getScheduleRoute("SCH001");
        assertNotNull(list);
    }

    @Test
    public void getScheduleRoute_voFieldsNotNull() {
        List<RouteScheduleVO> list = myScheduleRepository.getScheduleRoute("SCH001");
        assertTrue(list.isEmpty());
    }

    @Test
    public void getMapScheduleTest() {
        List<MapScheduleVO> list = myScheduleRepository.getMapSchedule("SCH001");
        assertNotNull(list);
    }

    @Test
    public void getMapSchedule_voFieldsNotNull() {
        List<MapScheduleVO> list = myScheduleRepository.getMapSchedule("SCH001");
        assertTrue(list.isEmpty());
    }

    @Test
    public void addVisitItemTest() {
        assertTrue(myScheduleRepository.addVisitItem(5, "1", "SCH011"));
    }

    @Test
    public void deleteVisitItemByIdTest() {
        assertFalse(myScheduleRepository.deleteVisitItemById("1"));
    }

    @Test
    public void addCompanionTest() {
        assertTrue(myScheduleRepository.addCompanion("SCH092", "frog9032"));
    }

    @Test
    public void setCompanionPermissionTest() {
        assertFalse(myScheduleRepository.setCompanionPermission("SCH002", "user01", "R"));
    }

    @Test
    public void getCompanionListTest() {
        List<ColleagueVO> list = myScheduleRepository.getCompanionList("SCH092");
        assertNotNull(list);
    }

    @Test
    public void getCompanionList_hasEntries() {
        List<ColleagueVO> list = myScheduleRepository.getCompanionList("SCH092");
        assertFalse("SCH001에 동행자가 존재해야 합니다", list.isEmpty());
    }

    @Test
    public void getCompanionList_emptySchedule_returnsEmptyList() {
        List<ColleagueVO> list = myScheduleRepository.getCompanionList("SCH092");
        assertNotNull(list);
    }

    @Test
    public void shareToPostTest() throws Exception {
        String result = myScheduleRepository.shareToPost("SCH092", "mskk0410", 0);
        assertEquals("SCH092", result);
    }

    @Test
    public void allocateNextMyScheduleIdTest() {
        String id = myScheduleRepository.allocateNextMyScheduleId();
        assertNotNull(id);
        assertTrue("ID는 SCH로 시작해야 합니다", id.startsWith("SCH"));
    }

    @Test
    public void insertMyScheduleRowTest() {
        String id = myScheduleRepository.allocateNextMyScheduleId();
        assertNotNull(id);
        assertTrue(myScheduleRepository.insertMyScheduleRow(id, "테스트 일정", "user01"));
    }


}
