package com.travel.letsgospringboot.place.service;

import com.travel.letsgospringboot.place.repository.PlaceMapper;
import com.travel.letsgospringboot.place.vo.PlaceVO;
import com.travel.letsgospringboot.place.vo.VisitItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {

    private final PlaceMapper placeMapper;

    public PlaceVO getPlaceByTitle(String placeType, String title) {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", placeType);
        params.put("title", title);
        return placeMapper.getPlaceByTitle(params);
    }

    public PlaceVO getPlaceByPlaceIdList(String placeId) {
        return placeMapper.getPlaceByPlaceIdList(placeId);
    }

    public PlaceVO getPlaceById(String placeId) {
        return placeMapper.getPlaceById(placeId);
    }

    public PlaceVO getPlaceByPlaceId(String placeId) {
        return placeMapper.getPlaceByPlaceId(placeId);
    }

    public List<PlaceVO> getPlaceByCategory(String placeType, String lclssystm3) {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", placeType);
        params.put("lclssystm3", lclssystm3);
        return placeMapper.getPlaceByCategory(params);
    }

    public List<PlaceVO> getPlaceOrderByLike(String placeType) {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", placeType);
        return placeMapper.getPlaceOrderByLike(params);
    }

    public List<PlaceVO> getPlaceOrderByTitle(String placeType) {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", placeType);
        return placeMapper.getPlaceOrderByTitle(params);
    }

    public List<PlaceVO> getPlaceByAddr(String placeType, String addrPattern) {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", placeType);
        String pattern = addrPattern.contains("%") ? addrPattern : "%" + addrPattern + "%";
        params.put("addrPattern", pattern);
        return placeMapper.getPlaceByAddr(params);
    }

    public List<PlaceVO> getPlaces() {
        return placeMapper.getPlaces();
    }

    public List<PlaceVO> getLeisurePlacesOrderByLikeDesc() {
        return placeMapper.getLeisurePlacesOrderByLikeDesc();
    }

    public List<PlaceVO> getLeisurePlaces() {
        return placeMapper.getLeisurePlaces();
    }

    public int getPlaceCount(String placeType) {
        return placeMapper.getPlaceCount(placeType);
    }

    @Transactional
    public int setPlaceLikeCount(String placeId) {
        return placeMapper.setPlaceLikeCount(placeId);
    }

    public int getPlaceLikeCount(String placeType, String placeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", placeType);
        params.put("placeId", placeId);
        return placeMapper.getPlaceLikeCount(params);
    }

    @Transactional
    public int setCounting(String placeId) {
        return placeMapper.setCounting(placeId);
    }

    @Transactional
    public int insertVisitItem(int visitOrder, Double distanceToNext, Long placeId, String scheduleId, String scheduleType) {
        Map<String, Object> params = new HashMap<>();
        params.put("visitOrder", visitOrder);
        params.put("distanceToNext", distanceToNext);
        params.put("placeId", placeId);
        params.put("scheduleId", scheduleId);
        params.put("scheduleType", scheduleType);
        return placeMapper.insertVisitItem(params);
    }

    public List<VisitItemVO> getVisitItemsByScheduleId(String scheduleId) {
        return placeMapper.getVisitItemsByScheduleId(scheduleId);
    }

    public List<PlaceVO> searchPlaces(String placeType, String category, String keyword, String sortBy) {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", placeType);

        boolean hasCategory = category != null && !category.trim().isEmpty();
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean isSortByLike = "like".equalsIgnoreCase(sortBy);

        if (hasCategory) {
            params.put("category", category);
        }
        if (hasKeyword) {
            String pattern = keyword.contains("%") ? keyword : "%" + keyword + "%";
            params.put("keywordPattern", pattern);
        }

        if (hasCategory && hasKeyword) {
            return isSortByLike 
                ? placeMapper.searchPlacesByCategoryAndKeywordOrderByLike(params)
                : placeMapper.searchPlacesByCategoryAndKeywordOrderByTitle(params);
        } else if (hasCategory) {
            return isSortByLike
                ? placeMapper.searchPlacesByCategoryOrderByLike(params)
                : placeMapper.searchPlacesByCategoryOrderByTitle(params);
        } else if (hasKeyword) {
            return isSortByLike
                ? placeMapper.searchPlacesByKeywordOrderByLike(params)
                : placeMapper.searchPlacesByKeywordOrderByTitle(params);
        } else {
            return isSortByLike
                ? placeMapper.searchPlacesOrderByLike(params)
                : placeMapper.searchPlacesOrderByTitle(params);
        }
    }

    public List<PlaceVO> searchNearbyPlaces(String placeType, String centerLat, String centerLon, Double radiusKm, boolean orderByLike, String category, String keyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", placeType);
        params.put("centerLat", centerLat);
        params.put("centerLon", centerLon);
        params.put("radiusKm", radiusKm);
        params.put("orderByLike", orderByLike);

        if (category != null && !category.trim().isEmpty()) {
            params.put("category", category);
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            String pattern = keyword.contains("%") ? keyword : "%" + keyword + "%";
            params.put("keywordPattern", pattern);
        }

        return placeMapper.searchNearbyPlaces(params);
    }


    public PlaceVO getPlaceByTitle(Map<String, Object> params) {
        return placeMapper.getPlaceByTitle(params);
    }

    public List<PlaceVO> getPlaceByCategory(Map<String, Object> params) {
        return placeMapper.getPlaceByCategory(params);
    }

    public List<PlaceVO> getPlaceOrderByLike(Map<String, Object> params) {
        return placeMapper.getPlaceOrderByLike(params);
    }

    public List<PlaceVO> getPlaceOrderByTitle(Map<String, Object> params) {
        return placeMapper.getPlaceOrderByTitle(params);
    }

    public List<PlaceVO> getPlaceByAddr(Map<String, Object> params) {
        return placeMapper.getPlaceByAddr(params);
    }

    public int getPlaceLikeCount(Map<String, Object> params) {
        return placeMapper.getPlaceLikeCount(params);
    }

    @Transactional
    public int insertVisitItem(Map<String, Object> params) {
        return placeMapper.insertVisitItem(params);
    }

    public List<PlaceVO> searchPlacesOrderByTitle(Map<String, Object> params) {
        return placeMapper.searchPlacesOrderByTitle(params);
    }

    public List<PlaceVO> searchPlacesOrderByLike(Map<String, Object> params) {
        return placeMapper.searchPlacesOrderByLike(params);
    }

    public List<PlaceVO> searchPlacesByCategoryOrderByTitle(Map<String, Object> params) {
        return placeMapper.searchPlacesByCategoryOrderByTitle(params);
    }

    public List<PlaceVO> searchPlacesByCategoryOrderByLike(Map<String, Object> params) {
        return placeMapper.searchPlacesByCategoryOrderByLike(params);
    }

    public List<PlaceVO> searchPlacesByKeywordOrderByTitle(Map<String, Object> params) {
        return placeMapper.searchPlacesByKeywordOrderByTitle(params);
    }

    public List<PlaceVO> searchPlacesByKeywordOrderByLike(Map<String, Object> params) {
        return placeMapper.searchPlacesByKeywordOrderByLike(params);
    }

    public List<PlaceVO> searchPlacesByCategoryAndKeywordOrderByTitle(Map<String, Object> params) {
        return placeMapper.searchPlacesByCategoryAndKeywordOrderByTitle(params);
    }

    public List<PlaceVO> searchPlacesByCategoryAndKeywordOrderByLike(Map<String, Object> params) {
        return placeMapper.searchPlacesByCategoryAndKeywordOrderByLike(params);
    }

    public List<PlaceVO> searchNearbyPlaces(Map<String, Object> params) {
        return placeMapper.searchNearbyPlaces(params);
    }

    @Transactional
    public int insertMySchedule(String myScheduleId, String title, java.time.LocalDateTime startAt, String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("myScheduleId", myScheduleId);
        params.put("title", title);
        params.put("startAt", startAt);
        params.put("userId", userId);
        return placeMapper.insertMySchedule(params);
    }
}
