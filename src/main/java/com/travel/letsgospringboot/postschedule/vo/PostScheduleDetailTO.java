package com.travel.letsgospringboot.postschedule.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostScheduleDetailTO {
    private String postId;
    private String scheduleTitle;
    private int likeCount;
    private int viewCount;
    private String writerId;
//    private boolean isOwner;

    private List<RouteScheduleTO> routes;
    private List<MapScheduleTO> maps;
}
