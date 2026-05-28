package com.travel.letsgospringboot.postschedule.vo;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CopyToVisitItemVO {
    private String myScheduleId;
    private String visitOrder;
    private double distanceToNext;
    private String placeId;
    private String scheduleType = "SCHEDULE";
}
