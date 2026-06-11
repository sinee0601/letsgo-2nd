package com.travel.letsgospringboot.tourapi.repository;

import com.travel.letsgospringboot.tourapi.vo.TourPlaceVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
public class TestTourImportMapper {

    @Autowired
    private TourImportMapper tourImportMapper;

    @Test
    public void testInsertPlace() {
        TourPlaceVO place = TourPlaceVO.builder()
                .title("테스트 장소")
                .addr1("서울특별시 중구 세종대로 110")
                .addr2("")
                .firstImage("https://example.com/image.jpg")
                .lclssystm1("AC")
                .lclssystm2("AC01")
                .lclssystm3("AC010100")
                .likeCount(0L)
                .mapx("126.981611")
                .mapy("37.568477")
                .placeType("LEISURE")
                .build();

        int rows = tourImportMapper.insertPlace(place);
        log.info("[TourImportMapper - insertPlace] 영향받은 행: {}", rows);

        assertThat(rows).isEqualTo(1);
    }
}
