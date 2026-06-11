package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScheduleTitleUpdateVO {
    private String title;
    private String scheduleId;
    private String userId;
}
