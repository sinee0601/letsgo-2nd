package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShareVO {
    private String scheduleId;
    private String userId;
    private int isAnonymous;
}
