package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class RouteScheduleVO {
    private String visitId;
    private String visitOrder;
    private String placeId;
    private String title;
    private double distanceToNext;
    private String scheduleType;
}
