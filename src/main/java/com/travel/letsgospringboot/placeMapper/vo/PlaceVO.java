package com.travel.letsgospringboot.placeMapper.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceVO {
    private Long placeId;
    private String title;
    private String firstImage;
    private String addr1;
    private String addr2;
    private String mapx;
    private String mapy;
    private Long likeCount;
    private String placeType;
}
