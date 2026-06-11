package com.travel.letsgospringboot.myschedule.service;

import com.travel.letsgospringboot.exception.AccessDeniedException;
import com.travel.letsgospringboot.myschedule.repository.MyScheduleRepository;
import com.travel.letsgospringboot.myschedule.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyScheduleService {
    private final MyScheduleRepository myScheduleRepository;

    public List<MyScheduleVO> getMyScheduleListAllByDate(String userId) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListAllByDate(userId));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListAllByTitle(String userId) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListAllByTitle(userId));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListSharedByDate(String userId) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListSharedByDate(userId));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListSharedByTitle(String userId) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListSharedByTitle(userId));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListSearchByDate(String userId, String keyword) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListSearchByDate(userId, keyword));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListSearchByTitle(String userId, String keyword) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListSearchByTitle(userId, keyword));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListSearchSharedByDate(String userId, String keyword) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListSearchSharedByDate(userId, keyword));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListSearchSharedByTitle(String userId, String keyword) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListSearchSharedByTitle(userId, keyword));
        return result;
    }

    private List<MyScheduleVO> processMyScheduleList(List<MyScheduleVO> data) {
        Map<String, MyScheduleVO> uniqueMap = new LinkedHashMap<>();
        for (MyScheduleVO vo : data) {
            if (!uniqueMap.containsKey(vo.getMyScheduleId())) {
                vo.setStartAt(formatStartAt(vo.getStartAt()));
                vo.setAddr1(formatAddr1(vo.getAddr1()));
                uniqueMap.put(vo.getMyScheduleId(), vo);
            } else {
                MyScheduleVO existingVO = uniqueMap.get(vo.getMyScheduleId());
                if (existingVO.getFirstImage() == null) {
                    existingVO.setFirstImage(vo.getFirstImage());
                }
                String combinedPlaces = existingVO.getPlaceTitle() + " / " + vo.getPlaceTitle();
                existingVO.setPlaceTitle(combinedPlaces);
            }
        }
        log.info(data.toString());
        List<MyScheduleVO> result = new ArrayList<>(uniqueMap.values());
        return result;
    }

    private String formatStartAt(String startAt) {
        if (startAt == null) {
            return null;
        }
        return startAt.length() >= 10 ? startAt.substring(0, 10) : startAt;
    }

    private String formatAddr1(String addr1) {
        if (addr1 == null) {
            return null;
        }
        String[] tokens = addr1.trim().split("\\s+");
        if (tokens.length <= 2) {
            return addr1.trim();
        }
        return tokens[0] + " " + tokens[1];
    }

    public boolean setMyScheduleTitle(String title, String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.setMyScheduleTitle(title, scheduleId, userId);
    }

    public boolean setTodoDetail(String scheduleId, String todoDetail, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.setTodoDetail(scheduleId, todoDetail);
    }

    public ScheduleDetailVO getScheduleDetail(String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.getScheduleDetail(scheduleId);
    }

    public String getTodoDetail(String scheduleId) {
        return myScheduleRepository.getTodoDetail(scheduleId);
    }

    public boolean setBudgetDetail(String scheduleId, String budgetDetail, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
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
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.setStartAt(scheduleId, startAt, userId);
    }

    public boolean addMySchedule(String myScheduleId, String title, String userId) {
        return myScheduleRepository.addMySchedule(myScheduleId, title, userId);
    }

    private boolean isScheduleOwnedByUser(String scheduleId, String userId) {
        if(myScheduleRepository.isScheduleOwnedByUser(scheduleId, userId) > 0)
            return true;
        return false;
    }

    public List<ScheduleSummaryVO> listMyScheduleIdAndTitle(String userId) {
        return myScheduleRepository.listMyScheduleIdAndTitle(userId);
    }

    public List<RouteScheduleVO> getScheduleRoute(String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.getScheduleRoute(scheduleId);
    }

    public List<MapScheduleVO> getMapSchedule(String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.getMapSchedule(scheduleId);
    }
    @Transactional
    public boolean addVisitItem(int visitOrder, String placeId, String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.addVisitItem(visitOrder, placeId, scheduleId);
    }
    @Transactional
    public boolean deleteVisitItemById(String visitItemId, String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.deleteVisitItemById(visitItemId);
    }
    @Transactional
    public boolean addCompanion(String myScheduleId, String sharedUserId) {
        return myScheduleRepository.addCompanion(myScheduleId, sharedUserId);
    }
    @Transactional
    public boolean setCompanionPermission(String scheduleId, String sharedUserId, String permission, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.setCompanionPermission(scheduleId, sharedUserId, permission);
    }
    @Transactional
    public List<ColleagueVO> getCompanionList(String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.getCompanionList(scheduleId);
    }

    @Transactional
    public boolean deleteMySchedule(String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
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
    public boolean updateVisitOrders(List<VisitOrderVO> orders, String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        boolean allUpdated = !orders.isEmpty();
        for (VisitOrderVO order : orders) {
            if (myScheduleRepository.updateVisitItem(
                    order.getVisitItemId(), order.getVisitOrder(), order.getDistance()) <= 0) {
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
    public String shareToPost(String scheduleId, String userId, int isAnonymous) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        myScheduleRepository.shareToPostInsert(scheduleId, userId, isAnonymous);
        myScheduleRepository.shareVisitItemsToPost(scheduleId);
        return myScheduleRepository.getLastPostId();
    }
}
