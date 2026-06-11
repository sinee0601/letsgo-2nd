package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BudgetUpdateVO {
    private String scheduleId;
    private String budgetDetail;
    private String userId;
}
