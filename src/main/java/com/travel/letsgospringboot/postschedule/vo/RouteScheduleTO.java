package com.travel.letsgospringboot.postschedule.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteScheduleTO {
    private String visitId;
    private String visitOrder;
    private String placeId;
    private String title;
    private double distanceToNext;
    private String scheduleType;
}
