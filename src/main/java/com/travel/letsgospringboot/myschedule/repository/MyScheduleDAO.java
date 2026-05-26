package com.travel.letsgospringboot.myschedule.repository;

import com.travel.letsgospringboot.myschedule.vo.ColleagueVO;
import com.travel.letsgospringboot.myschedule.vo.MapScheduleVO;
import com.travel.letsgospringboot.myschedule.vo.MyScheduleVO;
import com.travel.letsgospringboot.myschedule.vo.RouteScheduleVO;

import java.util.List;

public interface MyScheduleDAO {
    List<MyScheduleVO> getMyScheduleListAllByDate(String userId);

    List<MyScheduleVO> getMyScheduleListAllByTitle(String userId);

    List<MyScheduleVO> getMyScheduleListSharedByDate(String userId);

    List<MyScheduleVO> getMyScheduleListSharedByTitle(String userId);

    List<MyScheduleVO> getMyScheduleListSearchByDate(String userId, String keyword);

    List<MyScheduleVO> getMyScheduleListSearchByTitle(String userId, String keyword);

    List<MyScheduleVO> getMyScheduleListSearchSharedByDate(String userId, String keyword);

    List<MyScheduleVO> getMyScheduleListSearchSharedByTitle(String userId, String keyword);

    List<MyScheduleVO> getMyScheduleList(String userId, String keyword, String sortType, boolean sharedFilter);

    boolean deleteMySchedule(String scheduleId);

    boolean deleteMyScheduleList(String userId, String[] scheduleIds);

    boolean setMySchedule(String[] visitItemId, int[] visitOrder, String[] distanceToNext, String scheduleId,
                          String scheduleTitle, String startAt, String budgetDetail, String todoDetail, String userId, int isShared);

    boolean updateVisitOrders(String[] visitItemIds, int[] visitOrders, String[] distances);

    boolean setMyScheduleTitle(String title, String myScheduleId, String userId);

    boolean setTodoDetail(String scheduleId, String todoDetail);

    String getTodoDetail(String scheduleId);

    boolean setBudgetDetail(String scheduleId, String budgetDetail);

    String getBudgetDetail(String scheduleId);

    String getScheduleTitle(String scheduleId);

    String getStartAt(String scheduleId);

    boolean setStartAt(String scheduleId, String startAt, String userId);

    String allocateNextMyScheduleId();

    boolean insertMyScheduleRow(String myScheduleId, String title, String userId);

    boolean isScheduleOwnedByUser(String scheduleId, String userId);

    List<String[]> listMyScheduleIdAndTitle(String userId);

    List<RouteScheduleVO> getScheduleRoute(String scheduleId);

    List<MapScheduleVO> getMapSchedule(String scheduleId);

    boolean addVisitItem(int visitOrder, String placeId, String scheduleId);

    boolean deleteVisitItemById(String visitItemId);

    boolean addCompanion(String myScheduleId, String sharedUserId);

    boolean setCompanionPermission(String myScheduleId, String sharedUserId, String permission);

    List<ColleagueVO> getCompanionList(String myScheduleId);

    String shareToPost(String myScheduleId, String userId, int isAnonymous);

}
