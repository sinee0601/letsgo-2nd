package com.travel.letsgospringboot.place.service;

import com.travel.letsgospringboot.place.vo.PlaceVO;
import com.travel.letsgospringboot.place.vo.VisitItemVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(properties = {
        "mybatis.mapper-locations=classpath:mappers/placeMapper.xml"
})
@Transactional
public class TestPlaceService {

    @Autowired
    private PlaceService placeService;

    // 제목으로 장소 단일 조회 테스트
    @Test
    public void testGetPlaceByTitle() {
        PlaceVO place = placeService.getPlaceByTitle("LEISURE", "롤파크");
        log.info("[PlaceService - getPlaceByTitle] 결과: {}", place);
        assertThat(place).isNotNull();
        assertThat(place.getTitle()).isEqualTo("롤파크");
    }

    // 카테고리별 장소 리스트 조회 테스트
    @Test
    public void testGetPlaceByCategory() {
        List<PlaceVO> list = placeService.getPlaceByCategory("LEISURE", "LS011900");
        log.info("[PlaceService - getPlaceByCategory] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 좋아요순 장소 리스트 조회 테스트
    @Test
    public void testGetPlaceOrderByLike() {
        List<PlaceVO> list = placeService.getPlaceOrderByLike("LEISURE");
        log.info("[PlaceService - getPlaceOrderByLike] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 제목순 장소 리스트 조회 테스트
    @Test
    public void testGetPlaceOrderByTitle() {
        List<PlaceVO> list = placeService.getPlaceOrderByTitle("LEISURE");
        log.info("[PlaceService - getPlaceOrderByTitle] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 주소 패턴으로 장소 리스트 조회 테스트
    @Test
    public void testGetPlaceByAddr() {
        List<PlaceVO> list = placeService.getPlaceByAddr("LEISURE", "서울");
        log.info("[PlaceService - getPlaceByAddr] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // ID 기반 장소 바인딩 조회 테스트
    @Test
    public void testGetPlaceByPlaceIdList() {
        PlaceVO place = placeService.getPlaceByPlaceIdList("1");
        log.info("[PlaceService - getPlaceByPlaceIdList] 결과: {}", place);
        assertThat(place).isNotNull();
        assertThat(place.getPlaceId()).isEqualTo(1L);
    }

    // 장소 개수 조회 테스트
    @Test
    public void testGetPlaceCount() {
        int count = placeService.getPlaceCount("LEISURE");
        log.info("[PlaceService - getPlaceCount] 결과: {}", count);
        assertThat(count).isGreaterThan(0);
    }

    // 좋아요 수 1 증가 테스트
    @Test
    public void testSetPlaceLikeCount() {
        int rows = placeService.setPlaceLikeCount("1");
        log.info("[PlaceService - setPlaceLikeCount] 영향받은 행: {}", rows);
        assertThat(rows).isEqualTo(1);
    }

    // 장소 좋아요 수 조회 테스트
    @Test
    public void testGetPlaceLikeCount() {
        int likeCount = placeService.getPlaceLikeCount("LEISURE", "1");
        log.info("[PlaceService - getPlaceLikeCount] 결과: {}", likeCount);
    }

    // 전체 장소 리스트 조회 테스트
    @Test
    public void testGetPlaces() {
        List<PlaceVO> list = placeService.getPlaces();
        log.info("[PlaceService - getPlaces] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 방문 항목 삽입 및 스케줄 ID 기준 방문 리스트 조회 테스트
    @Test
    public void testInsertAndGetVisitItem() {
        int rows = placeService.insertVisitItem(2, 5.2, 2L, "TEST_SCH_002", "SCHEDULE");
        log.info("[PlaceService - insertVisitItem] 결과 행 수: {}", rows);
        assertThat(rows).isEqualTo(1);

        List<VisitItemVO> list = placeService.getVisitItemsByScheduleId("TEST_SCH_002");
        log.info("[PlaceService - getVisitItemsByScheduleId] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 레저 장소 좋아요 내림차순 리스트 조회 테스트
    @Test
    public void testGetLeisurePlacesOrderByLikeDesc() {
        List<PlaceVO> list = placeService.getLeisurePlacesOrderByLikeDesc();
        log.info("[PlaceService - getLeisurePlacesOrderByLikeDesc] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 레저 장소 전체 리스트 조회 테스트
    @Test
    public void testGetLeisurePlaces() {
        List<PlaceVO> list = placeService.getLeisurePlaces();
        log.info("[PlaceService - getLeisurePlaces] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 장소 카운팅 증가 테스트
    @Test
    public void testSetCounting() {
        int rows = placeService.setCounting("2");
        log.info("[PlaceService - setCounting] 결과 행 수: {}", rows);
        assertThat(rows).isEqualTo(1);
    }

    // ID로 장소 상세 조회 테스트
    @Test
    public void testGetPlaceById() {
        PlaceVO place = placeService.getPlaceById("1");
        log.info("[PlaceService - getPlaceById] 결과: {}", place);
        assertThat(place).isNotNull();
    }

    // ID로 장소 정보 조회 테스트
    @Test
    public void testGetPlaceByPlaceId() {
        PlaceVO place = placeService.getPlaceByPlaceId("2");
        log.info("[PlaceService - getPlaceByPlaceId] 결과: {}", place);
        assertThat(place).isNotNull();
    }

    // 다중 조건 검색 (카테고리, 키워드, 정렬) 통합 테스트
    @Test
    public void testSearchPlacesUnified() {
        // 1. 카테고리 단독 검색 (제목순 정렬)
        List<PlaceVO> list = placeService.searchPlaces("LEISURE", "LS011900", null, "title");
        log.info("[PlaceService - searchPlaces (Category only)] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();

        // 2. 키워드 단독 검색 (좋아요순 정렬)
        List<PlaceVO> list2 = placeService.searchPlaces("LEISURE", null, "북한산", "like");
        log.info("[PlaceService - searchPlaces (Keyword only)] 결과 개수: {}", list2.size());
        assertThat(list2).isNotEmpty();

        // 3. 카테고리 + 키워드 검색 (좋아요순 정렬)
        List<PlaceVO> list3 = placeService.searchPlaces("LEISURE", "LS011900", "북한산", "like");
        log.info("[PlaceService - searchPlaces (Category & Keyword)] 결과 개수: {}", list3.size());
        assertThat(list3).isNotEmpty();
    }

    // 반경 내 주변 장소 탐색 테스트
    @Test
    public void testSearchNearbyPlaces() {
        List<PlaceVO> list = placeService.searchNearbyPlaces(
                "LEISURE",
                "37.5948003229",
                "126.9429290191",
                10.0,
                true,
                null,
                null
        );
        log.info("[PlaceService - searchNearbyPlaces] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }
}
