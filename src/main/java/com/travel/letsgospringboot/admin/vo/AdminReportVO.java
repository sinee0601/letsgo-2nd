package com.travel.letsgospringboot.admin.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminReportVO {
    private Long reportId;
    private String postId;
    private String postTitle;
    private String reporterId;
    private String reason;
    private String status;
    private LocalDateTime createdAt;
}
