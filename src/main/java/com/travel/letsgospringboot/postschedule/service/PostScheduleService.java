package com.travel.letsgospringboot.postschedule.service;

import com.travel.letsgospringboot.postschedule.repository.PostScheduleRepository;
import com.travel.letsgospringboot.postschedule.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostScheduleService {

    @Autowired
    PostScheduleRepository postScheduleRepository;

//    public List<PostScheduleTO> getPostScheduleList(String sortOrder, String keyword) {
//        if (sortOrder == null || sortOrder.trim().isEmpty()) {
//            sortOrder = "latest";
//        }
//        if (keyword == null || keyword.trim().isEmpty()) {
//            switch (sortOrder) {
//                case "like":  return getPostScheduleListLike();
//                case "view":  return getPostScheduleListView();
//                case "title": return getPostScheduleListTitle();
//                default:      return getPostScheduleListLatest();
//            }
//        } else {
//            switch (sortOrder) {
//                case "like":  return getPostScheduleListSearchLike(keyword);
//                case "view":  return getPostScheduleListSearchView(keyword);
//                case "title": return getPostScheduleListSearchTitle(keyword);
//                default:      return getPostScheduleListSearchLatest(keyword);
//            }
//        }
//    }

    public List<PostScheduleTO> getPostScheduleListLike() {
        return postScheduleRepository.getPostScheduleListLike();
    }
    public List<PostScheduleTO> getPostScheduleListView() {
        return postScheduleRepository.getPostScheduleListView();
    }
    public List<PostScheduleTO> getPostScheduleListTitle() {
        return postScheduleRepository.getPostScheduleListTitle();
    }
    public List<PostScheduleTO> getPostScheduleListLatest() {
        return postScheduleRepository.getPostScheduleListLatest();
    }
    public List<PostScheduleTO> getPostScheduleListSearchLike(String keyword) {
        return postScheduleRepository.getPostScheduleListSearchLike(keyword);
    }
    public List<PostScheduleTO> getPostScheduleListSearchView(String keyword) {
        return postScheduleRepository.getPostScheduleListSearchView(keyword);
    }
    public List<PostScheduleTO> getPostScheduleListSearchTitle(String keyword) {
        return postScheduleRepository.getPostScheduleListSearchTitle(keyword);
    }
    public List<PostScheduleTO> getPostScheduleListSearchLatest(String keyword) {
        return postScheduleRepository.getPostScheduleListSearchLatest(keyword);
    }

//    public List<PostScheduleTO> getUserPostScheduleList(String userId, String sortOrder, String keyword) {
//        if (sortOrder == null || sortOrder.trim().isEmpty()) {
//            sortOrder = "latest";
//        }
//        if (keyword == null || keyword.trim().isEmpty()) {
//            switch (sortOrder) {
//                case "like":  return getUserPostScheduleListLike(userId);
//                case "view":  return getUserPostScheduleListView(userId);
//                case "title": return getUserPostScheduleListTitle(userId);
//                default:      return getUserPostScheduleListLatest(userId);
//            }
//        } else {
//            switch (sortOrder) {
//                case "like":  return getUserPostScheduleListSearchLike(userId, keyword);
//                case "view":  return getUserPostScheduleListSearchView(userId, keyword);
//                case "title": return getUserPostScheduleListSearchTitle(userId, keyword);
//                default:      return getUserPostScheduleListSearchLatest(userId, keyword);
//            }
//        }
//    }

    public List<PostScheduleTO> getUserPostScheduleListLike(String userId) {
        return postScheduleRepository.getUserPostScheduleListLike(userId);
    }
    public List<PostScheduleTO> getUserPostScheduleListView(String userId) {
        return postScheduleRepository.getUserPostScheduleListView(userId);
    }
    public List<PostScheduleTO> getUserPostScheduleListTitle(String userId) {
        return postScheduleRepository.getUserPostScheduleListTitle(userId);
    }
    public List<PostScheduleTO> getUserPostScheduleListLatest(String userId) {
        return postScheduleRepository.getUserPostScheduleListLatest(userId);
    }
    public List<PostScheduleTO> getUserPostScheduleListSearchLike(String userId, String keyword) {
        return postScheduleRepository.getUserPostScheduleListSearchLike(new UserPostScheduleListVO(userId, keyword));
    }
    public List<PostScheduleTO> getUserPostScheduleListSearchView(String userId, String keyword) {
        return postScheduleRepository.getUserPostScheduleListSearchView(new UserPostScheduleListVO(userId, keyword));
    }
    public List<PostScheduleTO> getUserPostScheduleListSearchTitle(String userId, String keyword) {
        return postScheduleRepository.getUserPostScheduleListSearchTitle(new UserPostScheduleListVO(userId, keyword));
    }
    public List<PostScheduleTO> getUserPostScheduleListSearchLatest(String userId, String keyword) {
        return postScheduleRepository.getUserPostScheduleListSearchLatest(new UserPostScheduleListVO(userId, keyword));
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
}
