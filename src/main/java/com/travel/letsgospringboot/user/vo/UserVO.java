package com.travel.letsgospringboot.user.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserVO {
    private String userID;
    private String email;
    private String name;
    private String password;
    private String role;
}
