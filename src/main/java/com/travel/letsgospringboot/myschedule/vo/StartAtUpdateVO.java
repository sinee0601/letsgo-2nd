package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StartAtUpdateVO {
    private String scheduleId;
    private String startAt;
    private String userId;
}
