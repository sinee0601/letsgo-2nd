package com.travel.letsgospringboot.postschedule.vo;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MapScheduleTO {
    private String visitOrder;
    private String title;
    private String mapX;
    private String mapY;
    private String distanceToNext;
}
