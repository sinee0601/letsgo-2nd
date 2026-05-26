package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class MyScheduleVO {
    private String myScheduleId;
    private String myScheduleTitle;
    private String startAt;
    private String isShared;
    private String placeTitle;
    private String addr1;
    private String firstImage;
}
