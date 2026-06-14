package com.travel.letsgospringboot.postschedule.service;

import com.travel.letsgospringboot.exception.AccessDeniedException;
import com.travel.letsgospringboot.exception.InvalidInputException;
import com.travel.letsgospringboot.exception.PostNotFoundException;
import com.travel.letsgospringboot.exception.AlreadyReportedException;
import com.travel.letsgospringboot.postschedule.repository.PostScheduleRepository;
import com.travel.letsgospringboot.postschedule.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostScheduleService {


    private final PostScheduleRepository postScheduleRepository;

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

    @Transactional
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
        int likeCount = postScheduleRepository.getLikeCount(postId);

        log.info("게시물 좋아요 증가: postId={}, likeCount={}", postId, likeCount);

        return likeCount;

    }

    @Transactional
    public int plusView(String postId) {
        postScheduleRepository.plusView(postId);
        int viewCount = postScheduleRepository.getViewCount(postId);

        log.info("게시물 조회수 증가: postId={}, viewCount={}", postId, viewCount);

        return viewCount;
    }

    public String getUserId(String postId) {
        return postScheduleRepository.getUserId(postId);
    }

    @Transactional
    public void deletePostSchedule(String postId, String loginUserId) {
        String writerId = getUserId(postId);

        if (!writerId.equals(loginUserId)) {
            log.warn("게시물 삭제 권한 위반: postId={}, loginUserId={}, writerId={}",
                    postId, loginUserId, writerId);
            throw new AccessDeniedException("게시물 삭제 권한이 없습니다.");
        }

        postScheduleRepository.deleteVisitItem(postId);
        postScheduleRepository.deleteSchedulePost(postId);

        log.info("게시물 삭제 완료: postId={}, loginUserId={}", postId, loginUserId);
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
        log.info("게시물 내 일정 추가 완료: postId={}, userId={}, myScheduleId={}",
                postId, userId, copyToMyScheduleVO.getGeneratedId());
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

        log.info("게시물 신고 등록 완료: postId={}, reporterId={}", postId, reporterId);
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
