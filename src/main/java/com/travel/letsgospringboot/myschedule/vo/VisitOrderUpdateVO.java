package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VisitOrderUpdateVO {
    private List<VisitOrderVO> orders;
    private String scheduleId;
    private String userId;
}
