package com.travel.letsgospringboot.postschedule.vo;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostScheduleListTO {
    private String postId;
    private String title;
    private int likeCount;
    private int viewCount;
    private int isAnonymous;
    private int isReported;
    private int isHidden;
    private String userName;
    private int visitItemId;
    private int visitOrder;
    private String scheduleType;
    private String placeTitle;
    private String addr1;
    private String firstImage;
    private String placeType;
}
