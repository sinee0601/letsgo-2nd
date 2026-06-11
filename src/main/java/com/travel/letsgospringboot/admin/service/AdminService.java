package com.travel.letsgospringboot.admin.service;

import com.travel.letsgospringboot.admin.repository.AdminRepository;
import com.travel.letsgospringboot.admin.vo.AdminPostVO;
import com.travel.letsgospringboot.admin.vo.AdminReportVO;
import com.travel.letsgospringboot.exception.CustomException;
import com.travel.letsgospringboot.place.vo.PlaceVO;
import com.travel.letsgospringboot.postschedule.repository.PostScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.letsgospringboot.exception.CustomException;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PostScheduleRepository postScheduleRepository;

    public int getTotalUserCount() {
        return adminRepository.selectTotalUserCount();
    }

    public int getTotalPlaceCount() {
        return adminRepository.selectTotalPlaceCount();
    }

    public int getTotalScheduleCount() {
        return adminRepository.selectTotalScheduleCount();
    }

    public List<PlaceVO> getRecentPlaces() {
        return adminRepository.selectRecentPlaces();
    }

    public List<PlaceVO> getAllPlaces() {
        return adminRepository.selectAdminPlaces(null);
    }

    public List<PlaceVO> searchPlaces(String keyword) {
        return adminRepository.selectAdminPlaces(keyword);
    }

    @Transactional
    public void insertPlace(PlaceVO placeVO) {
        adminRepository.insertPlace(placeVO);
    }

    @Transactional
    public void updatePlace(PlaceVO placeVO) {
        adminRepository.updatePlace(placeVO);
    }

    @Transactional
    public void deletePlace(Long placeId) {
        try {
            adminRepository.deletePlace(placeId);
        } catch (Exception e) {
            throw new CustomException("장소 삭제에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void togglePlaceVisibility(Long placeId, boolean isActive) {
        try {
            adminRepository.updatePlaceVisibility(placeId, isActive);
        } catch (Exception e) {
            throw new CustomException("상태 변경에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public List<AdminReportVO> getAllReports() {
        return adminRepository.selectAllReports();
    }

    @Transactional
    public void processReport(Long reportId, String action) {
        try {
            if ("REJECT".equals(action)) {
                adminRepository.updateReportStatus(reportId, "반려됨");
            } else if ("DELETE_POST".equals(action)) {
                AdminReportVO report = adminRepository.selectReportById(reportId);
                if (report != null) {
                    postScheduleRepository.deleteVisitItem(report.getPostId());
                    postScheduleRepository.deleteSchedulePost(report.getPostId());
                    adminRepository.updateReportStatus(reportId, "삭제완료");
                } else {
                    throw new CustomException("신고 내역을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new CustomException("잘못된 작업 요청입니다.", HttpStatus.BAD_REQUEST);
            }
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("처리에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public List<AdminPostVO> getAllPosts() {
        return adminRepository.selectAdminPosts(null);
    }

    public List<AdminPostVO> searchPosts(String keyword) {
        return adminRepository.selectAdminPosts(keyword);
    }

    @Transactional
    public void togglePostVisibility(String postId, boolean isActive) {
        try {
            int isHidden = isActive ? 0 : 1;
            adminRepository.updatePostVisibility(postId, isHidden);
        } catch (Exception e) {
            throw new CustomException("상태 변경에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void deletePost(String postId) {
        try {
            postScheduleRepository.deleteVisitItem(postId);
            postScheduleRepository.deleteSchedulePost(postId);
        } catch (Exception e) {
            throw new CustomException("게시글 삭제에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
