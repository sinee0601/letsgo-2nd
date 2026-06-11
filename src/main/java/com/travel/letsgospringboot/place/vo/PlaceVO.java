package com.travel.letsgospringboot.place.vo;

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
    private String lclssystm1;
    private String lclssystm2;
    private String lclssystm3;
    // 노출 여부 상태 플래그
    private boolean isActive;

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
