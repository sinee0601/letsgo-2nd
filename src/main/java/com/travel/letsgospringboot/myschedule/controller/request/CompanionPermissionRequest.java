package com.travel.letsgospringboot.myschedule.controller.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanionPermissionRequest {
    private String sharedUserId;
    private String permission;
}
