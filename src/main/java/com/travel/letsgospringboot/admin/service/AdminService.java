package com.travel.letsgospringboot.admin.service;

import com.travel.letsgospringboot.admin.repository.AdminRepository;
import com.travel.letsgospringboot.admin.vo.AdminPostVO;
import com.travel.letsgospringboot.admin.vo.AdminReportVO;
import com.travel.letsgospringboot.place.vo.PlaceVO;
import com.travel.letsgospringboot.postschedule.repository.PostScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void deletePlace(int placeId) {
        adminRepository.deletePlace(placeId);
    }

    @Transactional
    public void togglePlaceVisibility(int placeId, boolean isActive) {
        adminRepository.updatePlaceVisibility(placeId, isActive);
    }

    public List<AdminReportVO> getAllReports() {
        return adminRepository.selectAllReports();
    }

    @Transactional
    public void processReport(Long reportId, String action) {
        if ("REJECT".equals(action)) {
            adminRepository.updateReportStatus(reportId, "반려됨");
        } else if ("DELETE_POST".equals(action)) {
            AdminReportVO report = adminRepository.selectReportById(reportId);
            if (report != null) {
                postScheduleRepository.deleteVisitItem(report.getPostId());
                postScheduleRepository.deleteSchedulePost(report.getPostId());
                adminRepository.updateReportStatus(reportId, "삭제완료");
            }
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
        int isHidden = isActive ? 0 : 1;
        adminRepository.updatePostVisibility(postId, isHidden);
    }

    @Transactional
    public void deletePost(String postId) {
        postScheduleRepository.deleteVisitItem(postId);
        postScheduleRepository.deleteSchedulePost(postId);
    }
}
