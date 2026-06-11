package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VisitItemDeleteVO {
    private String visitItemId;
    private String scheduleId;
    private String userId;
}
