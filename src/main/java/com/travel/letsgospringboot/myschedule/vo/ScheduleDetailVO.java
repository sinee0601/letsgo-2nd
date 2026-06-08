package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ScheduleDetailVO {
    private String scheduleTitle;
    private String startAt;
    private String budgetDetail;
    private String todoDetail;
}
