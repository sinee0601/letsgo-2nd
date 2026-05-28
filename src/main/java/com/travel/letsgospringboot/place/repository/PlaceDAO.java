package com.travel.letsgospringboot.place.repository;

import com.travel.letsgospringboot.place.vo.PlaceVO;
import com.travel.letsgospringboot.place.vo.VisitItemVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PlaceDAO {

    PlaceVO getPlaceByTitle(Map<String, Object> params);

    List<PlaceVO> getPlaceByCategory(Map<String, Object> params);

    List<PlaceVO> getPlaceOrderByLike(Map<String, Object> params);

    List<PlaceVO> getPlaceOrderByTitle(Map<String, Object> params);

    List<PlaceVO> getPlaceByAddr(Map<String, Object> params);

    PlaceVO getPlaceByPlaceIdList(String placeId);

    int getPlaceCount(String placeType);

    int setPlaceLikeCount(String placeId);

    int getPlaceLikeCount(Map<String, Object> params);

    List<PlaceVO> getPlaces();

    int insertVisitItem(Map<String, Object> params);

    List<PlaceVO> getLeisurePlacesOrderByLikeDesc();

    List<PlaceVO> getLeisurePlaces();

    int setCounting(String placeId);

    PlaceVO getPlaceById(String placeId);

    List<VisitItemVO> getVisitItemsByScheduleId(String scheduleId);

    PlaceVO getPlaceByPlaceId(String placeId);

    List<PlaceVO> searchPlacesOrderByTitle(Map<String, Object> params);

    List<PlaceVO> searchPlacesOrderByLike(Map<String, Object> params);

    List<PlaceVO> searchPlacesByCategoryOrderByTitle(Map<String, Object> params);

    List<PlaceVO> searchPlacesByCategoryOrderByLike(Map<String, Object> params);

    List<PlaceVO> searchPlacesByKeywordOrderByTitle(Map<String, Object> params);

    List<PlaceVO> searchPlacesByKeywordOrderByLike(Map<String, Object> params);

    List<PlaceVO> searchPlacesByCategoryAndKeywordOrderByTitle(Map<String, Object> params);

    List<PlaceVO> searchPlacesByCategoryAndKeywordOrderByLike(Map<String, Object> params);

    List<PlaceVO> searchNearbyPlaces(Map<String, Object> params);
}

