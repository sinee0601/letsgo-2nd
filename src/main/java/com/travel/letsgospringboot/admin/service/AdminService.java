package com.travel.letsgospringboot.admin.service;

import com.travel.letsgospringboot.admin.repository.AdminRepository;
import com.travel.letsgospringboot.admin.vo.AdminPostVO;
import com.travel.letsgospringboot.admin.vo.AdminReportVO;
import com.travel.letsgospringboot.exception.*;
import com.travel.letsgospringboot.place.vo.PlaceVO;
import com.travel.letsgospringboot.postschedule.repository.PostScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
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
        log.info("장소 등록 성공: {}", placeVO.getTitle());
    }

    @Transactional
    public void updatePlace(PlaceVO placeVO) {
        adminRepository.updatePlace(placeVO);
        log.info("장소 수정 성공: id={}", placeVO.getPlaceId());
    }

    @Transactional
    public void deletePlace(Long placeId) {
        try {
            adminRepository.deletePlace(placeId);
            log.info("장소 삭제 성공: id={}", placeId);
        } catch (Exception e) {
            throw new PlaceOperationException("장소 삭제에 실패했습니다.");
        }
    }

    @Transactional
    public void togglePlaceVisibility(Long placeId, boolean isActive) {
        try {
            adminRepository.updatePlaceVisibility(placeId, isActive);
            log.info("장소 노출 여부 변경 성공: id={}, isActive={}", placeId, isActive);
        } catch (Exception e) {
            throw new PlaceOperationException("상태 변경에 실패했습니다.");
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
                log.info("신고 반려 처리 성공: reportId={}", reportId);
            } else if ("DELETE_POST".equals(action)) {
                AdminReportVO report = adminRepository.selectReportById(reportId);
                if (report != null) {
                    postScheduleRepository.deleteVisitItem(report.getPostId());
                    postScheduleRepository.deleteSchedulePost(report.getPostId());
                    adminRepository.updateReportStatus(reportId, "삭제완료");
                    log.info("신고 대상 게시글 삭제 및 완료 처리 성공: reportId={}, postId={}", reportId, report.getPostId());
                } else {
                    throw new ReportNotFoundException();
                }
            } else {
                throw new InvalidInputException("잘못된 작업 요청입니다.");
            }
        } catch (ReportNotFoundException | InvalidInputException e) {
            throw e;
        } catch (Exception e) {
            throw new ReportOperationException("처리에 실패했습니다.");
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
            log.info("게시글 노출 여부 변경 성공: postId={}, isActive={}", postId, isActive);
        } catch (Exception e) {
            throw new PostOperationException("상태 변경에 실패했습니다.");
        }
    }

    @Transactional
    public void deletePost(String postId) {
        try {
            postScheduleRepository.deleteVisitItem(postId);
            postScheduleRepository.deleteSchedulePost(postId);
            log.info("게시글 삭제 성공: postId={}", postId);
        } catch (Exception e) {
            throw new PostOperationException("게시글 삭제에 실패했습니다.");
        }
    }
}
