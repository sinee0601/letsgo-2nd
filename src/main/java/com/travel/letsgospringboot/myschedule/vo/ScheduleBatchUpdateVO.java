package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScheduleBatchUpdateVO {
    private String[] visitItemId;
    private int[] visitOrder;
    private String[] distanceToNext;
    private String scheduleId;
    private String scheduleTitle;
    private String startAt;
    private String budgetDetail;
    private String todoDetail;
    private String userId;
    private int isShared;
}
