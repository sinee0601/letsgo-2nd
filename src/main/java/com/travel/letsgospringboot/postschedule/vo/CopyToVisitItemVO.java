package com.travel.letsgospringboot.postschedule.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CopyToVisitItemVO {
    private String myScheduleId;
    private String visitOrder;
    private double distanceToNext;
    private String placeId;
    private String scheduleType = "SCHEDULE";
}
