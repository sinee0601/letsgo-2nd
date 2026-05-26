package com.travel.letsgospringboot.postschedule.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapScheduleTO {
    private String visitOrder;
    private String title;
    private String mapX;
    private String mapY;
    private String distanceToNext;
}
