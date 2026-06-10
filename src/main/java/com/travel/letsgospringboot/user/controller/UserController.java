package com.travel.letsgospringboot.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("loginView")
    public String loginView(){
        return "login";
    }
    @GetMapping("signUpView")
    public String signUpView(){
        return "signup";
    }
    @GetMapping("getIdView")
    public String getIdView(){
        return "getid";
    }
    @GetMapping("updatePwView")
    public String updatePwView(){
        return "updatepw";
    }
}
