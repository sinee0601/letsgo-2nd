package com.travel.letsgospringboot.postschedule.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostScheduleDetailTO {
    private String postId;
    private String scheduleTitle;
    private int likeCount;
    private int viewCount;
    private String writerId;
    private boolean isOwner;

    private List<RouteScheduleTO> routes;
    private List<MapScheduleTO> maps;

    private String budgetDetail;
    private String todoDetail;

    private int isHidden;
}
