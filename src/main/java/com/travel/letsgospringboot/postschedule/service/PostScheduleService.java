package com.travel.letsgospringboot.postschedule.service;

import com.travel.letsgospringboot.exception.AccessDeniedException;
import com.travel.letsgospringboot.exception.InvalidInputException;
import com.travel.letsgospringboot.exception.PostNotFoundException;
import com.travel.letsgospringboot.exception.AlreadyReportedException;
import com.travel.letsgospringboot.postschedule.repository.PostScheduleRepository;
import com.travel.letsgospringboot.postschedule.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostScheduleService {

    @Autowired
    PostScheduleRepository postScheduleRepository;

    public List<PostScheduleListTO> getPostScheduleListLike() {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListLike());
    }
    public List<PostScheduleListTO> getPostScheduleListView() {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListView());
    }
    public List<PostScheduleListTO> getPostScheduleListTitle() {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListTitle());
    }
    public List<PostScheduleListTO> getPostScheduleListLatest() {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListLatest());
    }
    public List<PostScheduleListTO> getPostScheduleListSearchLike(String keyword) {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListSearchLike(keyword));
    }
    public List<PostScheduleListTO> getPostScheduleListSearchView(String keyword) {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListSearchView(keyword));
    }
    public List<PostScheduleListTO> getPostScheduleListSearchTitle(String keyword) {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListSearchTitle(keyword));
    }
    public List<PostScheduleListTO> getPostScheduleListSearchLatest(String keyword) {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListSearchLatest(keyword));
    }

    public List<PostScheduleListTO> getUserPostScheduleListLike(String userId) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListLike(userId));
    }
    public List<PostScheduleListTO> getUserPostScheduleListView(String userId) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListView(userId));
    }
    public List<PostScheduleListTO> getUserPostScheduleListTitle(String userId) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListTitle(userId));
    }
    public List<PostScheduleListTO> getUserPostScheduleListLatest(String userId) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListLatest(userId));
    }
    public List<PostScheduleListTO> getUserPostScheduleListSearchLike(String userId, String keyword) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListSearchLike(new PostScheduleSearchConditionVO(userId, keyword)));
    }
    public List<PostScheduleListTO> getUserPostScheduleListSearchView(String userId, String keyword) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListSearchView(new PostScheduleSearchConditionVO(userId, keyword)));
    }
    public List<PostScheduleListTO> getUserPostScheduleListSearchTitle(String userId, String keyword) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListSearchTitle(new PostScheduleSearchConditionVO(userId, keyword)));
    }
    public List<PostScheduleListTO> getUserPostScheduleListSearchLatest(String userId, String keyword) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListSearchLatest(new PostScheduleSearchConditionVO(userId, keyword)));
    }

    public PostScheduleDetailTO getPostScheduleDetail(String postId, String loginUserId) {
        PostScheduleDetailTO detail = postScheduleRepository.getPostScheduleDetail(postId);
        if(detail == null || detail.getIsHidden() == 1)
            throw new PostNotFoundException("존재하지않는 게시물입니다.");

        boolean isOwner = loginUserId.equals(detail.getWriterId());

        detail.setOwner(isOwner);
        detail.setRoutes(postScheduleRepository.getScheduleRoute(postId));
        detail.setMaps(postScheduleRepository.getMapSchedule(postId));
        detail.setBudgetDetail(postScheduleRepository.getBudgetDetail(postId));
        detail.setTodoDetail(postScheduleRepository.getTodoDetail(postId));
        postScheduleRepository.plusView(postId);

        return detail;
    }

    public String getBudgetDetail(String postId) {
        return postScheduleRepository.getBudgetDetail(postId);
    }
    public String getTodoDetail(String postId) {
        return postScheduleRepository.getTodoDetail(postId);
    }

    public List<RouteScheduleTO> getScheduleRoute(String postId) {
        return postScheduleRepository.getScheduleRoute(postId);
    }
    public List<MapScheduleTO> getMapSchedule(String postId) {
        return postScheduleRepository.getMapSchedule(postId);
    }

    public String getScheduleTitle(String postId) {
        return postScheduleRepository.getScheduleTitle(postId);
    }

    @Transactional
    public int plusLike(String postId) {
        postScheduleRepository.plusLike(postId);
        return postScheduleRepository.getLikeCount(postId);
    }

    @Transactional
    public int plusView(String postId) {
        postScheduleRepository.plusView(postId);
        return postScheduleRepository.getViewCount(postId);
    }

    public String getUserId(String postId) {
        return postScheduleRepository.getUserId(postId);
    }

    @Transactional
    public void deletePostSchedule(String postId, String loginUserId) {
        postScheduleRepository.deleteVisitItem(postId);
        String writerId = getUserId(postId);

        if (!writerId.equals(loginUserId)) {
            throw new AccessDeniedException("게시물 삭제 권한이 없습니다.");
        }

        if(writerId.equals(loginUserId)){
            postScheduleRepository.deleteSchedulePost(postId);
        }
    }

    @Transactional
    public void addToMySchedule(String postId, String userId) {
        CopyToMyScheduleVO copyToMyScheduleVO = CopyToMyScheduleVO.builder()
                .title(postScheduleRepository.getScheduleTitle(postId))
                .budgetDetail(postScheduleRepository.getBudgetDetail(postId))
                .todoDetail(postScheduleRepository.getTodoDetail(postId))
                .userId(userId)
                .build();
        postScheduleRepository.copyToMySchedule(copyToMyScheduleVO);

        for (RouteScheduleTO route : postScheduleRepository.getScheduleRoute(postId)) {
            postScheduleRepository.copyToVisitItem(CopyToVisitItemVO.builder()
                    .myScheduleId(copyToMyScheduleVO.getGeneratedId())
                    .visitOrder(route.getVisitOrder())
                    .distanceToNext(route.getDistanceToNext())
                    .placeId(route.getPlaceId())
                    .scheduleType(route.getScheduleType())
                    .build());
        }
    }

    public void reportPostSchedule(String postId, String reporterId, String reason){
        if (reason == null || reason.trim().isEmpty()) {
            throw new InvalidInputException("신고 사유를 입력해주세요.");
        }
        PostScheduleDetailTO detail = postScheduleRepository.getPostScheduleDetail(postId);

        if (detail == null || detail.getIsHidden() == 1) {
            throw new PostNotFoundException("존재하지 않는 게시물입니다.");
        }

        if (postScheduleRepository.countReport(postId, reporterId) > 0) {
            throw new AlreadyReportedException("이미 신고한 게시물입니다.");
        }

        ReportPostScheduleVO reportPostScheduleVO = ReportPostScheduleVO.builder()
                .postId(postId)
                .reporterId(reporterId)
                .reason(reason)
                .status("대기중")
                .build();

        postScheduleRepository.reportPostSchedule(reportPostScheduleVO);
    }

    public List<PostScheduleListTO> processPostScheduleList(List<PostScheduleListTO> list){
        Map<String, PostScheduleListTO> uniqueMap = new LinkedHashMap<>();
        for (PostScheduleListTO to : list) {

            if (!uniqueMap.containsKey(to.getPostId())) {
                uniqueMap.put(to.getPostId(), to);
            } else {
                PostScheduleListTO existingVO = uniqueMap.get(to.getPostId());
                if (existingVO.getFirstImage() == null) {
                    existingVO.setFirstImage(to.getFirstImage());
                }
                String combinedPlaces = existingVO.getPlaceTitle() + " / " + to.getPlaceTitle();
                existingVO.setPlaceTitle(combinedPlaces);
            }
        }
        List<PostScheduleListTO> result = new ArrayList<>(uniqueMap.values());
        return result;
    }


}
