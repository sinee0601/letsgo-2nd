package com.travel.letsgospringboot.myschedule.controller.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanionRequest {
    private String sharedUserId;
}
