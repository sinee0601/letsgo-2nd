package com.travel.letsgospringboot.tourapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.letsgospringboot.tourapi.repository.TourImportMapper;
import com.travel.letsgospringboot.tourapi.vo.TourPlaceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TourImportService {

    private static final String BASE_URL = "https://apis.data.go.kr/B551011/KorService2/areaBasedList2";
    private static final int PAGE_SIZE = 100;
    private static final String SEOUL_REGION_CODE = "11";

    @Value("${tour.service-key}")
    private String serviceKey;

    private final TourImportMapper tourImportMapper;
    private final RestClient restClient = RestClient.create();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public int importLeisureSports() {
        return importByContentType(28, "LEISURE");
    }

    @Transactional
    public int importRestaurants() {
        return importByContentType(39, "RESTAURANT");
    }

    @Transactional
    public int importStays() {
        return importByContentType(32, "STAY");
    }

    private int importByContentType(int contentTypeId, String placeType) {
        JsonNode body = fetchPage(contentTypeId).path("response").path("body");
        return insertItems(body.path("items").path("item"), placeType);
    }

    private JsonNode fetchPage(int contentTypeId) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", PAGE_SIZE)
                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "AppTest")
                .queryParam("arrange", "C")
                .queryParam("contentTypeId", contentTypeId)
                .queryParam("lDongRegnCd", SEOUL_REGION_CODE)
                .queryParam("_type", "json")
                .build(true)
                .toUriString();

        String body = restClient.get().uri(url).retrieve().body(String.class);
        try {
            return objectMapper.readTree(body);
        } catch (Exception e) {
            throw new RuntimeException("투어 API 응답 파싱 실패: contentTypeId=" + contentTypeId, e);
        }
    }

    private int insertItems(JsonNode itemNode, String placeType) {
        int inserted = 0;
        for (JsonNode item : toList(itemNode)) {
            String mapx = text(item, "mapx");
            String mapy = text(item, "mapy");
            if (isBlank(mapx) || isBlank(mapy)) {
                continue;
            }

            TourPlaceVO place = TourPlaceVO.builder()
                    .title(text(item, "title"))
                    .addr1(text(item, "addr1"))
                    .addr2(text(item, "addr2"))
                    .firstImage(text(item, "firstimage"))
                    .lclssystm1(text(item, "lclsSystm1"))
                    .lclssystm2(text(item, "lclsSystm2"))
                    .lclssystm3(text(item, "lclsSystm3"))
                    .likeCount(0L)
                    .mapx(mapx)
                    .mapy(mapy)
                    .placeType(placeType)
                    .build();
            inserted += tourImportMapper.insertPlace(place);
        }
        return inserted;
    }

    private List<JsonNode> toList(JsonNode itemNode) {
        List<JsonNode> list = new ArrayList<>();
        if (itemNode.isArray()) {
            itemNode.forEach(list::add);
        } else if (itemNode.isObject()) {
            list.add(itemNode);
        }
        return list;
    }

    private String text(JsonNode item, String field) {
        JsonNode value = item.path(field);
        return value.isMissingNode() || value.isNull() ? null : value.asText();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
