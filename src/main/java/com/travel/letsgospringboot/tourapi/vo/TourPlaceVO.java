package com.travel.letsgospringboot.tourapi.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourPlaceVO {
    private String title;
    private String addr1;
    private String addr2;
    private String firstImage;
    private String lclssystm1;
    private String lclssystm2;
    private String lclssystm3;
    private Long likeCount;
    private String mapx;
    private String mapy;
    private String placeType;
}
