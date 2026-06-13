package com.travel.letsgospringboot.myschedule.controller.request;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private String searchTitle;
    private boolean Shared;
    private String sortType;
}
