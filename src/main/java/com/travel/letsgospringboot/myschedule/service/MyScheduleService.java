package com.travel.letsgospringboot.myschedule.service;

import com.travel.letsgospringboot.myschedule.repository.MyScheduleRepository;
import com.travel.letsgospringboot.myschedule.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyScheduleService {
    private final MyScheduleRepository myScheduleRepository;

    // ===== 단순 위임 =====
    public List<MyScheduleVO> getMyScheduleListAllByDate(String userId) {
        return myScheduleRepository.getMyScheduleListAllByDate(userId);
    }

    public List<MyScheduleVO> getMyScheduleListAllByTitle(String userId) {
        return myScheduleRepository.getMyScheduleListAllByTitle(userId);
    }

    public List<MyScheduleVO> getMyScheduleListSharedByDate(String userId) {
        return myScheduleRepository.getMyScheduleListSharedByDate(userId);
    }

    public List<MyScheduleVO> getMyScheduleListSharedByTitle(String userId) {
        return myScheduleRepository.getMyScheduleListSharedByTitle(userId);
    }

    public List<MyScheduleVO> getMyScheduleListSearchByDate(String userId, String keyword) {
        return myScheduleRepository.getMyScheduleListSearchByDate(userId, keyword);
    }

    public List<MyScheduleVO> getMyScheduleListSearchByTitle(String userId, String keyword) {
        return myScheduleRepository.getMyScheduleListSearchByTitle(userId, keyword);
    }

    public List<MyScheduleVO> getMyScheduleListSearchSharedByDate(String userId, String keyword) {
        return myScheduleRepository.getMyScheduleListSearchSharedByDate(userId, keyword);
    }

    public List<MyScheduleVO> getMyScheduleListSearchSharedByTitle(String userId, String keyword) {
        return myScheduleRepository.getMyScheduleListSearchSharedByTitle(userId, keyword);
    }

    public List<MyScheduleVO> getMyScheduleList(String userId, String keyword, String sortType, boolean sharedFilter) {
        return myScheduleRepository.getMyScheduleList(userId, keyword, sortType, sharedFilter);
    }

    public boolean setMyScheduleTitle(String title, String myScheduleId, String userId) {
        return myScheduleRepository.setMyScheduleTitle(title, myScheduleId, userId);
    }

    public boolean setTodoDetail(String scheduleId, String todoDetail) {
        return myScheduleRepository.setTodoDetail(scheduleId, todoDetail);
    }

    public String getTodoDetail(String scheduleId) {
        return myScheduleRepository.getTodoDetail(scheduleId);
    }

    public boolean setBudgetDetail(String scheduleId, String budgetDetail) {
        return myScheduleRepository.setBudgetDetail(scheduleId, budgetDetail);
    }

    public String getBudgetDetail(String scheduleId) {
        return myScheduleRepository.getBudgetDetail(scheduleId);
    }

    public String getScheduleTitle(String scheduleId) {
        return myScheduleRepository.getScheduleTitle(scheduleId);
    }

    public String getStartAt(String scheduleId) {
        return myScheduleRepository.getStartAt(scheduleId);
    }

    public boolean setStartAt(String scheduleId, String startAt, String userId) {
        return myScheduleRepository.setStartAt(scheduleId, startAt, userId);
    }

    public boolean insertMyScheduleRow(String myScheduleId, String title, String userId) {
        return myScheduleRepository.insertMyScheduleRow(myScheduleId, title, userId);
    }

    public int isScheduleOwnedByUser(String scheduleId, String userId) {
        return myScheduleRepository.isScheduleOwnedByUser(scheduleId, userId);
    }

    public List<ScheduleSummaryVO> listMyScheduleIdAndTitle(String userId) {
        return myScheduleRepository.listMyScheduleIdAndTitle(userId);
    }

    public List<RouteScheduleVO> getScheduleRoute(String scheduleId) {
        return myScheduleRepository.getScheduleRoute(scheduleId);
    }

    public List<MapScheduleVO> getMapSchedule(String scheduleId) {
        return myScheduleRepository.getMapSchedule(scheduleId);
    }

    public boolean addVisitItem(int visitOrder, String placeId, String scheduleId) {
        return myScheduleRepository.addVisitItem(visitOrder, placeId, scheduleId);
    }

    public boolean deleteVisitItemById(String visitItemId) {
        return myScheduleRepository.deleteVisitItemById(visitItemId);
    }

    public boolean addCompanion(String myScheduleId, String sharedUserId) {
        return myScheduleRepository.addCompanion(myScheduleId, sharedUserId);
    }

    public boolean setCompanionPermission(String myScheduleId, String sharedUserId, String permission) {
        return myScheduleRepository.setCompanionPermission(myScheduleId, sharedUserId, permission);
    }

    public List<ColleagueVO> getCompanionList(String myScheduleId) {
        return myScheduleRepository.getCompanionList(myScheduleId);
    }
    //트랜잭션 메서드
    @Transactional
    public boolean deleteMySchedule(String scheduleId) {
        myScheduleRepository.deleteVisitItemsByScheduleId(scheduleId);
        return myScheduleRepository.deleteScheduleById(scheduleId) > 0;
    }

    @Transactional
    public boolean deleteMyScheduleList(String userId, String[] scheduleIds) {
        boolean allDeleted = scheduleIds.length > 0;
        for (String id : scheduleIds) {
            myScheduleRepository.deleteVisitItemsByScheduleId(id);
            if (myScheduleRepository.deleteScheduleByIdAndUserId(id, userId) <= 0) {
                allDeleted = false;
            }
        }
        return allDeleted;
    }

    @Transactional
    public boolean setMySchedule(String[] visitItemId, int[] visitOrder, String[] distanceToNext, String scheduleId,
                                 String scheduleTitle, String startAt, String budgetDetail, String todoDetail,
                                 String userId, int isShared) {
        boolean updated = myScheduleRepository.updateSchedule(scheduleId, scheduleTitle, startAt, budgetDetail,
                todoDetail, isShared, userId) > 0;
        for (int i = 0; i < visitItemId.length; i++) {
            myScheduleRepository.updateVisitItem(visitItemId[i], visitOrder[i], distanceToNext[i]);
        }
        return updated;
    }

    @Transactional
    public boolean updateVisitOrders(String[] visitItemIds, int[] visitOrders, String[] distances) {
        boolean allUpdated = visitItemIds.length > 0;
        for (int i = 0; i < visitItemIds.length; i++) {
            if (myScheduleRepository.updateVisitItem(visitItemIds[i], visitOrders[i], distances[i]) <= 0) {
                allUpdated = false;
            }
        }
        return allUpdated;
    }

    public String allocateNextMyScheduleId() {
        String candidate;
        do {
            candidate = myScheduleRepository.getNextMyScheduleIdCandidate();
        } while (myScheduleRepository.checkMyScheduleIdExists(candidate) > 0);
        return candidate;
    }

    @Transactional
    public String shareToPost(String myScheduleId, String userId, int isAnonymous) {
        myScheduleRepository.shareToPostInsert(myScheduleId, userId, isAnonymous);
        myScheduleRepository.shareVisitItemsToPost(myScheduleId);
        return myScheduleRepository.getLastPostId();
    }
}
