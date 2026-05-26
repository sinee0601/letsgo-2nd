package com.travel.letsgospringboot.postschedule.repository;

import com.travel.letsgospringboot.postschedule.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostScheduleRepository {
    //일정 리스트 조회
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

    List<PostScheduleTO> getUserPostScheduleListSearchLike(UserPostScheduleListTO userPostScheduleListTO);
    List<PostScheduleTO> getUserPostScheduleListSearchView(UserPostScheduleListTO userPostScheduleListTO);
    List<PostScheduleTO> getUserPostScheduleListSearchTitle(UserPostScheduleListTO userPostScheduleListTO);
    List<PostScheduleTO> getUserPostScheduleListSearchLatest(UserPostScheduleListTO userPostScheduleListTO);


    //예산 및 할일 상세 조회
    String getBudgetDetail(String postId);
    String getTodoDetail(String postId);

    //경로 및 지도 데이터 조회
    List<RouteScheduleTO> getScheduleRoute(String postId);
    List<MapScheduleTO> getMapSchedule(String postId);

    //제목 조회
    String getScheduleTitle(String postId);

    //일정 삭제 (VisitItem과 SchedulePost 연쇄 삭제 대응)
    boolean deleteSchedulePost(String postId);
    boolean deleteVisitItem(String postId);

    //좋아요 및 조회수 조회
    int getLikeCount(String postId);
    int getViewCount(String postId);

    //좋아요 및 조회수 증가
    boolean plusLike(String postId);
    boolean plusView(String postId);

    //작성자 ID 조회
    String getUserId(String postId);

    //내 일정으로 복사 (생성된 PK 문자열 반환)
     void copyToMySchedule(CopyToMyScheduleVO copyToMyScheduleVO);

    //방문 항목 복사
    boolean copyToVisitItem(CopyToVisitItemVO copyToVisitItemVO);
}
