package com.travel.letsgospringboot.repository;

import com.travel.letsgospringboot.postschedule.repository.PostScheduleRepository;
import com.travel.letsgospringboot.postschedule.vo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TestPostScheduleRepository {

    @Autowired
    PostScheduleRepository postScheduleRepository;

    @Test
    void getPostScheduleListLike(){
        List<PostScheduleListTO> list = postScheduleRepository.getPostScheduleListLike();
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListView(){
        List<PostScheduleListTO> list = postScheduleRepository.getPostScheduleListView();
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListTitle(){
        List<PostScheduleListTO> list = postScheduleRepository.getPostScheduleListTitle();
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListLatest(){
        List<PostScheduleListTO> list = postScheduleRepository.getPostScheduleListLatest();
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListLikeKeyword(){
        List<PostScheduleListTO> list = postScheduleRepository.getPostScheduleListSearchLike("여의도");
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListViewKeyword(){
        List<PostScheduleListTO> list = postScheduleRepository.getPostScheduleListSearchView("여의도");
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListTitleKeyword(){
        List<PostScheduleListTO> list = postScheduleRepository.getPostScheduleListSearchTitle("여의도");
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListLatestKeyword(){
        List<PostScheduleListTO> list = postScheduleRepository.getPostScheduleListSearchLatest("여의도");
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListLikeUser(){
        List<PostScheduleListTO> list = postScheduleRepository.getUserPostScheduleListLike("user01");
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListViewUser(){
        List<PostScheduleListTO> list = postScheduleRepository.getUserPostScheduleListView("user01");
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListTitleUser(){
        List<PostScheduleListTO> list = postScheduleRepository.getUserPostScheduleListTitle("user01");
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListLatestUser(){
        List<PostScheduleListTO> list = postScheduleRepository.getUserPostScheduleListLatest("user01");
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListLikeUserKeyword(){
        List<PostScheduleListTO> list = postScheduleRepository.getUserPostScheduleListSearchLike(new PostScheduleSearchConditionVO("user01", "여의도"));
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListViewUserKeyword(){
        List<PostScheduleListTO> list = postScheduleRepository.getUserPostScheduleListSearchView(new PostScheduleSearchConditionVO("user01", "여의도"));
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListTitleUserKeyword(){
        List<PostScheduleListTO> list = postScheduleRepository.getUserPostScheduleListSearchTitle(new PostScheduleSearchConditionVO("user01", "여의도"));
        assertNotNull(list);
    }

    @Test
    void getPostScheduleListLatestUserKeyword(){
        List<PostScheduleListTO> list = postScheduleRepository.getUserPostScheduleListSearchLatest(new PostScheduleSearchConditionVO("user01", "여의도"));
        assertNotNull(list);
    }

    @Test
    void getBudgetAndTodoDetail() {
        String postId = "P001";

        String budgetDetail = postScheduleRepository.getBudgetDetail(postId);
        assertEquals("15만원", budgetDetail);

        String todoDetail = postScheduleRepository.getTodoDetail(postId);
        assertEquals("북한산 산책 후 함박스테이크 맛집 방문", todoDetail);
    }

    @Test
    void getScheduleRoute() {
        String postId = "P001";

        List<RouteScheduleTO> routeList = postScheduleRepository.getScheduleRoute(postId);
        assertNotNull(routeList);
        assertFalse(routeList.isEmpty());
        assertEquals("1", routeList.get(0).getPlaceId());
        assertEquals("1", routeList.get(0).getVisitOrder());
    }

    @Test
    void getMapSchedule() {
        String postId = "P001";

        List<MapScheduleTO> mapList = postScheduleRepository.getMapSchedule(postId);
        assertNotNull(mapList);
        assertFalse(mapList.isEmpty());
        assertEquals("북한산 자락길", mapList.get(0).getTitle());
        assertEquals("1", mapList.get(0).getVisitOrder());
    }

    @Test
    void getScheduleTitle() {
        String postId = "P001";
        String title = postScheduleRepository.getScheduleTitle(postId);
        assertEquals("서울 도심 속 숲세권, 북한산 자락길 힐링 투어", title);
    }

    @Test
    void getCountsAndUserId() {
        String postId = "P001";

        int likeCount = postScheduleRepository.getLikeCount(postId);
        assertEquals(46, likeCount);

        int viewCount = postScheduleRepository.getViewCount(postId);
        assertEquals(321, viewCount);

        String userId = postScheduleRepository.getUserId(postId);
        assertEquals("user01", userId);
    }

    @Test
    void plusLikeAndView() {
        String postId = "P001";

        int beforeLike = postScheduleRepository.getLikeCount(postId);
        boolean isLikeUpdated = postScheduleRepository.plusLike(postId);
        int afterLike = postScheduleRepository.getLikeCount(postId);

        assertTrue(isLikeUpdated);
        assertEquals(beforeLike + 1, afterLike);

        int beforeView = postScheduleRepository.getViewCount(postId);
        boolean isViewUpdated = postScheduleRepository.plusView(postId);
        int afterView = postScheduleRepository.getViewCount(postId);

        assertTrue(isViewUpdated);
        assertEquals(beforeView + 1, afterView);
    }

    @Test
    void copyScheduleAndVisitItems() {
        String title = "복사된 힐링 일정";
        String budget = "20만원";
        String todo = "복사본 일정대로 움직이기";
        String targetUserId = "user02";

        CopyToMyScheduleVO vo = new CopyToMyScheduleVO();
        vo.setTitle(title);
        vo.setBudgetDetail(budget);
        vo.setTodoDetail(todo);
        vo.setUserId(targetUserId);
        postScheduleRepository.copyToMySchedule(vo);
        String generatedMyScheduleId = vo.getGeneratedId();

        assertNotNull(generatedMyScheduleId);
        assertTrue(generatedMyScheduleId.startsWith("S"));

        RouteScheduleTO mockRoute = new RouteScheduleTO();
        mockRoute.setVisitOrder("1");
        mockRoute.setDistanceToNext(5.5);
        mockRoute.setPlaceId("1");
        mockRoute.setScheduleType("SCHEDULE");

        CopyToVisitItemVO visit = new CopyToVisitItemVO();
        visit.setMyScheduleId(generatedMyScheduleId);
        visit.setVisitOrder(mockRoute.getVisitOrder());
        visit.setDistanceToNext(mockRoute.getDistanceToNext());
        visit.setPlaceId(mockRoute.getPlaceId());
        visit.setScheduleType(mockRoute.getScheduleType());
        boolean isRouteCopied = postScheduleRepository.copyToVisitItem(visit);
        assertTrue(isRouteCopied);
    }

    @Test
    void deletePostScheduleAndItems() {
        String postId = "P001";

        boolean isItemsDeleted = postScheduleRepository.deleteVisitItem(postId);
        assertTrue(isItemsDeleted);

        boolean isPostDeleted = postScheduleRepository.deleteSchedulePost(postId);
        assertTrue(isPostDeleted);

        String titleAfterDelete = postScheduleRepository.getScheduleTitle(postId);
        assertNull(titleAfterDelete);
    }
}
