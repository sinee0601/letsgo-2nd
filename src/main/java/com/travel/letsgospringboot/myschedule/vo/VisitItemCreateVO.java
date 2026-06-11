package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VisitItemCreateVO {
    private int visitOrder;
    private String placeId;
    private String scheduleId;
    private String userId;
}
