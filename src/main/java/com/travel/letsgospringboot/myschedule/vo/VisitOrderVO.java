package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VisitOrderVO {
    private String visitItemId;
    private int visitOrder;
    private String distance;
}
