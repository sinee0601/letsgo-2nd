package com.travel.letsgospringboot.myschedule.repository;

import com.travel.letsgospringboot.myschedule.vo.ColleagueVO;
import com.travel.letsgospringboot.myschedule.vo.MapScheduleVO;
import com.travel.letsgospringboot.myschedule.vo.MyScheduleVO;
import com.travel.letsgospringboot.myschedule.vo.RouteScheduleVO;
import com.travel.letsgospringboot.myschedule.vo.ScheduleSummaryVO;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

@Transactional
public class TestMyScheduleRepository {
    @Autowired
    MyScheduleRepository myScheduleRepository;

    @Test
    void testGetMyScheduleListAllByDate() {
        System.out.println(myScheduleRepository.getMyScheduleListAllByDate("user01"));
    }

    @Test
    void getMyScheduleListAllByDate_existingUser_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListAllByDate("user01"));
    }

    @Test
    void getMyScheduleListAllByDate_existingUser_returnsItems() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByDate("user01");
        assertFalse(result.isEmpty());
    }

    @Test
    void getMyScheduleListAllByDate_voFieldsNotNull() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByDate("user01");
        MyScheduleVO first = result.get(0);
        assertNotNull(first.getMyScheduleId());
        assertNotNull(first.getMyScheduleTitle());
    }

    @Test
    void getMyScheduleListAllByDate_nonexistentUser_returnsEmptyList() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByDate("noSuchUser9999");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getMyScheduleListAllByTitle_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListAllByTitle("user01"));
    }

    @Test
    void getMyScheduleListAllByTitle_nonexistentUser_returnsEmptyList() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListAllByTitle("noSuchUser9999");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getMyScheduleListSharedByDate_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListSharedByDate("user01"));
    }

    @Test
    void getMyScheduleListSharedByDate_returnsItems() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListSharedByDate("user01");
        assertFalse(result.isEmpty());
    }

    @Test
    void getMyScheduleListSharedByTitle_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListSharedByTitle("user01"));
    }

    @Test
    void getMyScheduleListSearchByDate_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListSearchByDate("user01", "서대문"));
        assertNotNull(myScheduleRepository.getMyScheduleListSearchByDate("user01", ""));
    }

    @Test
    void getMyScheduleListSearchByDate_keywordMatch_returnsResults() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListSearchByDate("user01", "서대문");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getMyScheduleListSearchByTitle_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListSearchByTitle("user01", "서대문"));
        assertNotNull(myScheduleRepository.getMyScheduleListSearchByTitle("user01", ""));
    }

    @Test
    void getMyScheduleListSearchByTitle_noMatch_returnsEmptyList() {
        List<MyScheduleVO> result = myScheduleRepository.getMyScheduleListSearchByTitle("user01", "절대매칭안되는키워드xyzxyz");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getMyScheduleListSearchSharedByDate_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListSearchSharedByDate("user01", "서대문"));
        assertNotNull(myScheduleRepository.getMyScheduleListSearchSharedByDate("user01", ""));
    }

    @Test
    void getMyScheduleListSearchSharedByTitle_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleListSearchSharedByTitle("user01", "서대문"));
        assertNotNull(myScheduleRepository.getMyScheduleListSearchSharedByTitle("user01", ""));
    }

    @Test
    void getMyScheduleList_returnsNonNull() {
        assertNotNull(myScheduleRepository.getMyScheduleList("user01", "서대문", "title", false));
        assertNotNull(myScheduleRepository.getMyScheduleList("user01", "", "title", false));
        assertNotNull(myScheduleRepository.getMyScheduleList("user01", null, "date", true));
    }

    @Test
    void getScheduleTitle_returnsNonNull() {
        assertNotNull(myScheduleRepository.getScheduleTitle("S002"));
    }

    @Test
    void getStartAt_returnsNonNull() {
        assertNotNull(myScheduleRepository.getStartAt("S002"));
    }

    @Test
    void setAndGetTodoDetail() {
        assertTrue(myScheduleRepository.setTodoDetail("S002", "햄부기 사냥함부기"));
        assertEquals("햄부기 사냥함부기", myScheduleRepository.getTodoDetail("S002"));
    }

    @Test
    void setAndGetBudgetDetail() {
        assertTrue(myScheduleRepository.setBudgetDetail("S002", "1인당 2만원"));
        assertEquals("1인당 2만원", myScheduleRepository.getBudgetDetail("S002"));
    }

    @Test
    void setStartAt_ownedSchedule_returnsTrue() {
        assertTrue(myScheduleRepository.setStartAt("S002", "2026-05-20", "user01"));
    }

    @Test
    void setMyScheduleTitle_ownedSchedule_returnsTrue() {
        assertTrue(myScheduleRepository.setMyScheduleTitle("새 제목 테스트", "S002", "user01"));
    }

    @Test
    void getMapSchedule_returnsItems() {
        List<MapScheduleVO> list = myScheduleRepository.getMapSchedule("S001");
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertNotNull(list.get(0).getTitle());
    }

    @Test
    void addVisitItem_returnsTrue() {
        assertTrue(myScheduleRepository.addVisitItem(5, "1", "S002"));
    }

    @Test
    void deleteVisitItemById_nonexistent_returnsFalse() {
        assertFalse(myScheduleRepository.deleteVisitItemById("nonexistent-visit-item"));
    }

    @Test
    void addCompanion_returnsTrue() {
        assertTrue(myScheduleRepository.addCompanion("S002", "user04"));
    }

    @Test
    void setCompanionPermission_existingShare_returnsTrue() {
        assertTrue(myScheduleRepository.setCompanionPermission("S001", "user02", "W"));
    }

    @Test
    void getCompanionList_hasEntries() {
        List<ColleagueVO> list = myScheduleRepository.getCompanionList("S001");
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void getCompanionList_noShare_returnsEmptyList() {
        List<ColleagueVO> list = myScheduleRepository.getCompanionList("S999");
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void insertMyScheduleRow_returnsTrue() {
        assertTrue(myScheduleRepository.insertMyScheduleRow("S999", "테스트 일정", "user01"));
    }

    @Test
    void isScheduleOwnedByUser() {
        assertNotNull(myScheduleRepository.isScheduleOwnedByUser("S002", "user01"));
        assertEquals(0, myScheduleRepository.isScheduleOwnedByUser("S002", "user99"));
    }

    @Test
    void listMyScheduleIdAndTitle() {
        List<ScheduleSummaryVO> list = myScheduleRepository.listMyScheduleIdAndTitle("user01");
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertNotNull(list.get(0).getMyScheduleId());
        assertNotNull(list.get(0).getTitle());
    }

    @Test
    void getScheduleRoute_returnsNonNull() {
        List<RouteScheduleVO> list = myScheduleRepository.getScheduleRoute("S001");
        assertNotNull(list);
    }
}