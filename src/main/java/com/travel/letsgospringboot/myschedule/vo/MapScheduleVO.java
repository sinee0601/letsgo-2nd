package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class MapScheduleVO {
    private String visitOrder;
    private String title;
    private String mapX;
    private String mapY;
    private String distanceToNext;
}
