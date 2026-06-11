package com.travel.letsgospringboot.tourapi.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class TestTourImportService {

    @Autowired
    private TourImportService tourImportService;

    @Test
    public void testImportLeisureSports() {
        int inserted = tourImportService.importLeisureSports();
        log.info("[TourImportService - importLeisureSports] 적재 건수: {}", inserted);
        assertThat(inserted).isGreaterThan(0);
    }

    @Test
    public void testImportRestaurants() {
        int inserted = tourImportService.importRestaurants();
        log.info("[TourImportService - importRestaurants] 적재 건수: {}", inserted);
        assertThat(inserted).isGreaterThan(0);
    }

    @Test
    public void testImportStays() {
        int inserted = tourImportService.importStays();
        log.info("[TourImportService - importStays] 적재 건수: {}", inserted);
        assertThat(inserted).isGreaterThan(0);
    }
}
