package com.travel.letsgospringboot.place.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitItemVO {
    private String visitItemId;
    private int visitOrder;
    private Double distanceToNext;
    private Long placeId;
    private String scheduleId;
    private String scheduleType;
}
