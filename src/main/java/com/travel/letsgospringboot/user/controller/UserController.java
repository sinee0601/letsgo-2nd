package com.travel.letsgospringboot.user.controller;

import com.travel.letsgospringboot.user.service.UserService;
import com.travel.letsgospringboot.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    @ResponseBody
    public String root(){
        return "root";
    }
    @GetMapping("user")
    @ResponseBody
    public String user(){
        return "user";
    }
    @GetMapping("loginView")
    public String loginView(Model model){
        model.addAttribute("message", "처음 로그인 시도");
        return "login";
    }
    @GetMapping("signUpView")
    public String signUpView(){
        return "signUp";
    }
    @PostMapping("signUp")
    public String signUp(UserRequest userRequest){
        if (userService.signUp(UserVO.builder()
                .userID(userRequest.getUserID())
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .password(userRequest.getPassword())
                .build()))
            return "redirect:/user/login";
        return "redirect:/user/signUp";
    }
}
