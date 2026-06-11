package com.travel.letsgospringboot.myschedule.controller.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VisitItemRequest {
    private int visitOrder;
    private String placeId;
}
