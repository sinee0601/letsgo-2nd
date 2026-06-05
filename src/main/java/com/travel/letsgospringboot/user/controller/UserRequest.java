package com.travel.letsgospringboot.user.controller;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"password"})
public class UserRequest {
    private String userID;
    private String email;
    private String name;
    private String password;
}
