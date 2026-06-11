package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TodoUpdateVO {
    private String scheduleId;
    private String todoDetail;
    private String userId;
}
