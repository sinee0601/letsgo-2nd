package com.travel.letsgospringboot.admin.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminPostVO {
    private String postId;
    private String title;
    private String userId;
    private LocalDateTime postedAt;
    private boolean isActive;
}
