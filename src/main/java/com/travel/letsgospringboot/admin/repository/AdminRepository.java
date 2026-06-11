package com.travel.letsgospringboot.admin.repository;

import com.travel.letsgospringboot.admin.vo.AdminPostVO;
import com.travel.letsgospringboot.admin.vo.AdminReportVO;
import com.travel.letsgospringboot.place.vo.PlaceVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminRepository {
    int selectTotalUserCount();
    int selectTotalPlaceCount();
    int selectTotalScheduleCount();
    List<PlaceVO> selectRecentPlaces();

    List<PlaceVO> selectAdminPlaces(@Param("keyword") String keyword);
    int insertPlace(PlaceVO placeVO);
    int updatePlace(PlaceVO placeVO);
    int deletePlace(@Param("placeId") int placeId);
    int updatePlaceVisibility(@Param("placeId") int placeId, @Param("isActive") boolean isActive);

    List<AdminReportVO> selectAllReports();
    int updateReportStatus(@Param("reportId") Long reportId, @Param("status") String status);
    AdminReportVO selectReportById(@Param("reportId") Long reportId);

    List<AdminPostVO> selectAdminPosts(@Param("keyword") String keyword);
    int updatePostVisibility(@Param("postId") String postId, @Param("isHidden") int isHidden);
}
