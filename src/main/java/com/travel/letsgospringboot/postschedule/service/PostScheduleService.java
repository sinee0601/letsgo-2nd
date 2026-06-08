package com.travel.letsgospringboot.postschedule.service;

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



    public List<PostScheduleTO> getPostScheduleListLike() {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListLike());
    }
    public List<PostScheduleTO> getPostScheduleListView() {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListView());
    }
    public List<PostScheduleTO> getPostScheduleListTitle() {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListTitle());
    }
    public List<PostScheduleTO> getPostScheduleListLatest() {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListLatest());
    }
    public List<PostScheduleTO> getPostScheduleListSearchLike(String keyword) {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListSearchLike(keyword));
    }
    public List<PostScheduleTO> getPostScheduleListSearchView(String keyword) {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListSearchView(keyword));
    }
    public List<PostScheduleTO> getPostScheduleListSearchTitle(String keyword) {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListSearchTitle(keyword));
    }
    public List<PostScheduleTO> getPostScheduleListSearchLatest(String keyword) {
        return processPostScheduleList(postScheduleRepository.getPostScheduleListSearchLatest(keyword));
    }

    public List<PostScheduleTO> getUserPostScheduleListLike(String userId) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListLike(userId));
    }
    public List<PostScheduleTO> getUserPostScheduleListView(String userId) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListView(userId));
    }
    public List<PostScheduleTO> getUserPostScheduleListTitle(String userId) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListTitle(userId));
    }
    public List<PostScheduleTO> getUserPostScheduleListLatest(String userId) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListLatest(userId));
    }
    public List<PostScheduleTO> getUserPostScheduleListSearchLike(String userId, String keyword) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListSearchLike(new UserPostScheduleListVO(userId, keyword)));
    }
    public List<PostScheduleTO> getUserPostScheduleListSearchView(String userId, String keyword) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListSearchView(new UserPostScheduleListVO(userId, keyword)));
    }
    public List<PostScheduleTO> getUserPostScheduleListSearchTitle(String userId, String keyword) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListSearchTitle(new UserPostScheduleListVO(userId, keyword)));
    }
    public List<PostScheduleTO> getUserPostScheduleListSearchLatest(String userId, String keyword) {
        return processPostScheduleList(postScheduleRepository.getUserPostScheduleListSearchLatest(new UserPostScheduleListVO(userId, keyword)));
    }

    public PostScheduleDetailTO getPostScheduleDetail(String postId, String loingUserId) {
        PostScheduleDetailTO detail = postScheduleRepository.getPostScheduleDetail(postId);
        boolean isOwner = loingUserId.equals(detail.getWriterId());
        detail.setOwner(isOwner);
        detail.setRoutes(getScheduleRoute(postId));
        detail.setMaps(getMapSchedule(postId));
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

    public int getLikeCount(String postId) {
        return postScheduleRepository.getLikeCount(postId);
    }
    public int getViewCount(String postId) {
        return postScheduleRepository.getViewCount(postId);
    }

    public void plusLike(String postId) {
        postScheduleRepository.plusLike(postId);
    }
    public void plusView(String postId) {
        postScheduleRepository.plusView(postId);
    }

    public String getUserId(String postId) {
        return postScheduleRepository.getUserId(postId);
    }

    @Transactional
    public void deletePostSchedule(String postId) {
        postScheduleRepository.deleteVisitItem(postId);
        postScheduleRepository.deleteSchedulePost(postId);
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

    public List<PostScheduleTO> processPostScheduleList(List<PostScheduleTO> list){
        Map<String, PostScheduleTO> uniqueMap = new LinkedHashMap<>();
        for (PostScheduleTO to : list) {

            if (!uniqueMap.containsKey(to.getPostId())) {
                uniqueMap.put(to.getPostId(), to);
            } else {
                PostScheduleTO existingVO = uniqueMap.get(to.getPostId());
                if (existingVO.getFirstImage() == null) {
                    existingVO.setFirstImage(to.getFirstImage());
                }
                String combinedPlaces = existingVO.getPlaceTitle() + " / " + to.getPlaceTitle();
                existingVO.setPlaceTitle(combinedPlaces);
            }
        }
        List<PostScheduleTO> result = new ArrayList<>(uniqueMap.values());
        return result;
    }


}
