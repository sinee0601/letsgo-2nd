package com.travel.letsgospringboot.postschedule.repository;

import com.travel.letsgospringboot.postschedule.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostScheduleRepository {
    List<PostScheduleListTO> getPostScheduleListLike();
    List<PostScheduleListTO> getPostScheduleListView();
    List<PostScheduleListTO> getPostScheduleListTitle();
    List<PostScheduleListTO> getPostScheduleListLatest();

    List<PostScheduleListTO> getPostScheduleListSearchLike(String keyword);
    List<PostScheduleListTO> getPostScheduleListSearchView(String keyword);
    List<PostScheduleListTO> getPostScheduleListSearchTitle(String keyword);
    List<PostScheduleListTO> getPostScheduleListSearchLatest(String keyword);

    List<PostScheduleListTO> getUserPostScheduleListLike(String userId);
    List<PostScheduleListTO> getUserPostScheduleListView(String userId);
    List<PostScheduleListTO> getUserPostScheduleListTitle(String userId);
    List<PostScheduleListTO> getUserPostScheduleListLatest(String userId);

    List<PostScheduleListTO> getUserPostScheduleListSearchLike(PostScheduleSearchConditionVO postScheduleSearchConditionVO);
    List<PostScheduleListTO> getUserPostScheduleListSearchView(PostScheduleSearchConditionVO postScheduleSearchConditionVO);
    List<PostScheduleListTO> getUserPostScheduleListSearchTitle(PostScheduleSearchConditionVO postScheduleSearchConditionVO);
    List<PostScheduleListTO> getUserPostScheduleListSearchLatest(PostScheduleSearchConditionVO postScheduleSearchConditionVO);

    String getBudgetDetail(String postId);
    String getTodoDetail(String postId);

    List<RouteScheduleTO> getScheduleRoute(String postId);
    List<MapScheduleTO> getMapSchedule(String postId);

    PostScheduleDetailTO getPostScheduleDetail(String postId);

    String getScheduleTitle(String postId);

    boolean deleteSchedulePost(String postId);
    boolean deleteVisitItem(String postId);

    int getLikeCount(String postId);
    int getViewCount(String postId);

    boolean plusLike(String postId);
    boolean plusView(String postId);

    String getUserId(String postId);

    void copyToMySchedule(CopyToMyScheduleVO copyToMyScheduleVO);

    int countReport(String postId, String reporterId);

    void reportPostSchedule(ReportPostScheduleVO reportPostScheduleVO);

    boolean copyToVisitItem(CopyToVisitItemVO copyToVisitItemVO);


}
