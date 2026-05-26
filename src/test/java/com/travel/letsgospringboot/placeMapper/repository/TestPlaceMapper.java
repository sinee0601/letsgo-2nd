package com.travel.letsgospringboot.placeMapper.repository;

import com.travel.letsgospringboot.placeMapper.vo.PlaceVO;
import com.travel.letsgospringboot.placeMapper.vo.VisitItemVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(properties = {
        "mybatis.mapper-locations=classpath:mappers/placeMapper.xml"
})
@Transactional
public class TestPlaceMapper {

    @Autowired
    private SqlSession sqlSession;

    // 제목으로 장소 단일 조회 테스트
    @Test
    public void testGetPlaceByTitle() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");
        params.put("title", "롤파크");

        PlaceVO place = sqlSession.selectOne("PlaceMapper.getPlaceByTitle", params);
        log.info("[제목으로 장소 조회] 결과: {}", place);

        assertThat(place).isNotNull();
        assertThat(place.getTitle()).isEqualTo("롤파크");
    }

    // 카테고리별 장소 리스트 조회 테스트
    @Test
    public void testGetPlaceByCategory() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");
        params.put("lclssystm3", "LS011900");

        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.getPlaceByCategory", params);
        log.info("[카테고리별 장소 조회] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 좋아요순 장소 리스트 조회 테스트
    @Test
    public void testGetPlaceOrderByLike() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");

        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.getPlaceOrderByLike", params);
        log.info("[좋아요순 장소 조회] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 제목순 장소 리스트 조회 테스트
    @Test
    public void testGetPlaceOrderByTitle() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");

        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.getPlaceOrderByTitle", params);
        log.info("[제목순 장소 조회] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 주소 패턴으로 장소 리스트 조회 테스트
    @Test
    public void testGetPlaceByAddr() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");
        params.put("addrPattern", "%서울%");

        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.getPlaceByAddr", params);
        log.info("[주소 패턴으로 장소 조회] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // ID 기반 장소 바인딩 조회 테스트
    @Test
    public void testGetPlaceByPlaceIdList() {
        String placeId = "1";
        PlaceVO place = sqlSession.selectOne("PlaceMapper.getPlaceByPlaceIdList", placeId);
        log.info("[ID 기반 장소 바인딩 조회] 결과: {}", place);

        assertThat(place).isNotNull();
        assertThat(place.getPlaceId()).isEqualTo(1L);
    }

    // 장소 개수 조회 테스트
    @Test
    public void testGetPlaceCount() {
        int count = sqlSession.selectOne("PlaceMapper.getPlaceCount", "LEISURE");
        log.info("[장소 개수 조회] 결과: {}", count);
        assertThat(count).isGreaterThan(0);
    }

    // 좋아요 수 1 증가 테스트
    @Test
    public void testSetPlaceLikeCount() {
        String placeId = "1";
        sqlSession.update("PlaceMapper.setPlaceLikeCount", placeId);
        log.info("[좋아요 수 증가] placeId: 1의 좋아요 수 업데이트 완료");
    }

    // 장소 좋아요 수 조회 테스트
    @Test
    public void testGetPlaceLikeCount() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");
        params.put("placeId", "1");

        int likeCount = sqlSession.selectOne("PlaceMapper.getPlaceLikeCount", params);
        log.info("[장소 좋아요 수 조회] 결과: {}", likeCount);
    }

    // 전체 장소 리스트 조회 테스트
    @Test
    public void testGetPlaces() {
        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.getPlaces");
        log.info("[전체 장소 조회] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 방문 항목 삽입 및 스케줄 ID 기준 방문 리스트 조회 테스트
    @Test
    public void testInsertAndGetVisitItem() {
        Map<String, Object> params = new HashMap<>();
        params.put("visitOrder", 2);
        params.put("distanceToNext", 5.2);
        params.put("placeId", 2L);
        params.put("scheduleId", "TEST_SCH_002");
        params.put("scheduleType", "SCHEDULE");

        int rows = sqlSession.insert("PlaceMapper.insertVisitItem", params);
        log.info("[방문 항목 삽입] 결과 행 수: {}", rows);
        assertThat(rows).isEqualTo(1);

        List<VisitItemVO> list = sqlSession.selectList("PlaceMapper.getVisitItemsByScheduleId", "TEST_SCH_002");
        log.info("[스케줄 ID 기준 방문 리스트 조회] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 레저 장소 좋아요 내림차순 리스트 조회 테스트
    @Test
    public void testGetLeisurePlacesOrderByLikeDesc() {
        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.getLeisurePlacesOrderByLikeDesc");
        log.info("[레저 장소 좋아요 내림차순 조회] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 레저 장소 전체 리스트 조회 테스트
    @Test
    public void testGetLeisurePlaces() {
        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.getLeisurePlaces");
        log.info("[레저 장소 전체 조회] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 장소 카운팅 증가 테스트
    @Test
    public void testSetCounting() {
        String placeId = "2";
        int rows = sqlSession.update("PlaceMapper.setCounting", placeId);
        log.info("[장소 카운팅 증가] 결과 행 수: {}", rows);
        assertThat(rows).isEqualTo(1);
    }

    // ID로 장소 상세 조회 테스트
    @Test
    public void testGetPlaceById() {
        PlaceVO place = sqlSession.selectOne("PlaceMapper.getPlaceById", "1");
        log.info("[ID로 장소 조회] 결과: {}", place);
        assertThat(place).isNotNull();
    }

    // ID로 장소 정보 조회 테스트
    @Test
    public void testGetPlaceByPlaceId() {
        PlaceVO place = sqlSession.selectOne("PlaceMapper.getPlaceByPlaceId", "2");
        log.info("[ID로 장소 정보 조회] 결과: {}", place);
        assertThat(place).isNotNull();
    }

    // 장소 검색 (제목순 정렬) 테스트
    @Test
    public void testSearchPlacesOrderByTitle() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");

        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.searchPlacesOrderByTitle", params);
        log.info("[장소 검색 - 제목순 정렬] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 장소 검색 (좋아요순 정렬) 테스트
    @Test
    public void testSearchPlacesOrderByLike() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");

        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.searchPlacesOrderByLike", params);
        log.info("[장소 검색 - 좋아요순 정렬] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 카테고리별 장소 검색 (제목순 정렬) 테스트
    @Test
    public void testSearchPlacesByCategoryOrderByTitle() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");
        params.put("category", "LS011900");

        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.searchPlacesByCategoryOrderByTitle", params);
        log.info("[카테고리별 장소 검색 - 제목순 정렬] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 카테고리별 장소 검색 (좋아요순 정렬) 테스트
    @Test
    public void testSearchPlacesByCategoryOrderByLike() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");
        params.put("category", "LS011900");

        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.searchPlacesByCategoryOrderByLike", params);
        log.info("[카테고리별 장소 검색 - 좋아요순 정렬] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 키워드 기준 장소 검색 (제목순 정렬) 테스트
    @Test
    public void testSearchPlacesByKeywordOrderByTitle() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");
        params.put("keywordPattern", "%북한산%");

        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.searchPlacesByKeywordOrderByTitle", params);
        log.info("[키워드 기준 장소 검색 - 제목순 정렬] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 키워드 기준 장소 검색 (좋아요순 정렬) 테스트
    @Test
    public void testSearchPlacesByKeywordOrderByLike() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");
        params.put("keywordPattern", "%북한산%");

        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.searchPlacesByKeywordOrderByLike", params);
        log.info("[키워드 기준 장소 검색 - 좋아요순 정렬] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 카테고리 및 키워드 기준 장소 검색 (제목순 정렬) 테스트
    @Test
    public void testSearchPlacesByCategoryAndKeywordOrderByTitle() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");
        params.put("category", "LS011900");
        params.put("keywordPattern", "%북한산%");

        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.searchPlacesByCategoryAndKeywordOrderByTitle", params);
        log.info("[카테고리 및 키워드 장소 검색 - 제목순 정렬] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 카테고리 및 키워드 기준 장소 검색 (좋아요순 정렬) 테스트
    @Test
    public void testSearchPlacesByCategoryAndKeywordOrderByLike() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");
        params.put("category", "LS011900");
        params.put("keywordPattern", "%북한산%");

        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.searchPlacesByCategoryAndKeywordOrderByLike", params);
        log.info("[카테고리 및 키워드 장소 검색 - 좋아요순 정렬] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }

    // 반경 내 주변 장소 탐색 테스트
    @Test
    public void testSearchNearbyPlaces() {
        Map<String, Object> params = new HashMap<>();
        params.put("placeType", "LEISURE");
        params.put("centerLat", "37.5948003229");
        params.put("centerLon", "126.9429290191");
        params.put("radiusKm", 10.0);
        params.put("orderByLike", true);

        List<PlaceVO> list = sqlSession.selectList("PlaceMapper.searchNearbyPlaces", params);
        log.info("[반경 내 주변 장소 탐색] 결과 개수: {}", list.size());
        assertThat(list).isNotEmpty();
    }
}
