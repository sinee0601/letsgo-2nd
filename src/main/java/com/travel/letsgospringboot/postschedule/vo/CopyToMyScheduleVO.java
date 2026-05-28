package com.travel.letsgospringboot.postschedule.vo;

import lombok.*;


@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CopyToMyScheduleVO {
    private String generatedId;
    private String title;
    private String budgetDetail;
    private String todoDetail;
    private String userId;
}
