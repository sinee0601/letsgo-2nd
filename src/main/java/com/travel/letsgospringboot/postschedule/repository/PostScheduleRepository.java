package com.travel.letsgospringboot.postschedule.repository;

import com.travel.letsgospringboot.postschedule.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostScheduleRepository {
    List<PostScheduleTO> getPostScheduleListLike();
    List<PostScheduleTO> getPostScheduleListView();
    List<PostScheduleTO> getPostScheduleListTitle();
    List<PostScheduleTO> getPostScheduleListLatest();

    List<PostScheduleTO> getPostScheduleListSearchLike(String keyword);
    List<PostScheduleTO> getPostScheduleListSearchView(String keyword);
    List<PostScheduleTO> getPostScheduleListSearchTitle(String keyword);
    List<PostScheduleTO> getPostScheduleListSearchLatest(String keyword);

    List<PostScheduleTO> getUserPostScheduleListLike(String userId);
    List<PostScheduleTO> getUserPostScheduleListView(String userId);
    List<PostScheduleTO> getUserPostScheduleListTitle(String userId);
    List<PostScheduleTO> getUserPostScheduleListLatest(String userId);

    List<PostScheduleTO> getUserPostScheduleListSearchLike(UserPostScheduleListVO userPostScheduleListVO);
    List<PostScheduleTO> getUserPostScheduleListSearchView(UserPostScheduleListVO userPostScheduleListVO);
    List<PostScheduleTO> getUserPostScheduleListSearchTitle(UserPostScheduleListVO userPostScheduleListVO);
    List<PostScheduleTO> getUserPostScheduleListSearchLatest(UserPostScheduleListVO userPostScheduleListVO);

    String getBudgetDetail(String postId);
    String getTodoDetail(String postId);

    List<RouteScheduleTO> getScheduleRoute(String postId);
    List<MapScheduleTO> getMapSchedule(String postId);

    String getScheduleTitle(String postId);

    boolean deleteSchedulePost(String postId);
    boolean deleteVisitItem(String postId);

    int getLikeCount(String postId);
    int getViewCount(String postId);

    boolean plusLike(String postId);
    boolean plusView(String postId);

    String getUserId(String postId);

    void copyToMySchedule(CopyToMyScheduleVO copyToMyScheduleVO);

    boolean copyToVisitItem(CopyToVisitItemVO copyToVisitItemVO);
}
