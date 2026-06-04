package com.travel.letsgospringboot.myschedule.controller;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private String userId = "user01";
    private String searchTitle = "";
    private boolean isShared = false;
    private String sortType;
}
