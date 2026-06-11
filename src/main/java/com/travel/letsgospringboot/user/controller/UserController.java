package com.travel.letsgospringboot.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("loginView")
    public String loginView(@RequestParam(value = "error", required = false) String error, Model model){
        if (error != null) {
            String errorMessage = "아이디/비밀번호를 다시 입력하세요.";
            if ("disabled".equals(error)) {
                errorMessage = "정지된 계정입니다.";
            }
            model.addAttribute("errorMessage", errorMessage);
        }
        return "login";
    }

    @GetMapping("signUpView")
    public String signUpView() {
        return "signup";
    }

    @GetMapping("getIdView")
    public String getIdView() {
        return "getid";
    }

    @GetMapping("updatePwView")
    public String updatePwView() {
        return "updatepw";
    }
}
