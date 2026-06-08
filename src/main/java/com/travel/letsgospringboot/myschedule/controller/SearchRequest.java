package com.travel.letsgospringboot.myschedule.controller;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private String userId;
    private String searchTitle;
    private boolean isShared;
    private String sortType;
}
