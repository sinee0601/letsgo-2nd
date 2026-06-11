package com.travel.letsgospringboot.postschedule.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostScheduleSearchConditionVO {
    private String userId;
    private String keyword;
}
