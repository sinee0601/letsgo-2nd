package com.travel.letsgospringboot.myschedule.controller.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScheduleTitleRequest {
    private String title;
}
