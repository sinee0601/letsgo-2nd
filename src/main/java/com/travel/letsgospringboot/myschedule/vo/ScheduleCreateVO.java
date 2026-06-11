package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScheduleCreateVO {
    private String myScheduleId;
    private String title;
    private String userId;
}
