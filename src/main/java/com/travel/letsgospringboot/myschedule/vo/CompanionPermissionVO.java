package com.travel.letsgospringboot.myschedule.vo;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanionPermissionVO {
    private String scheduleId;
    private String sharedUserId;
    private String permission;
    private String userId;
}
