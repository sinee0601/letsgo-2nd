package com.travel.letsgospringboot.postschedule.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostScheduleTO {
    private String postId;
    private String title;
    private int likeCount;
    private int viewCount;
    private int isAnonymous;
    private String userName;
    private int visitItemId;
    private int visitOrder;
    private String scheduleType;
    private String placeTitle;
    private String addr1;
    private String firstImage;
    private String placeType;
}
