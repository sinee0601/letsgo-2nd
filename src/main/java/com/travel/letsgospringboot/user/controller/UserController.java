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

    //private final UserService userService;

    @GetMapping("user")
    @ResponseBody
    public String user(){
        return "user";
    }
    @GetMapping("loginView")
    public String loginView(){
        return "login";
    }
    @GetMapping("signUpView")
    public String signUpView(){
        return "signup";
    }
//    @PostMapping("signUp")
//    public String signUp(UserRequest userRequest, Model model){
//        String userId = userRequest.getUserID();
//        String name = userRequest.getName();
//        String email = userRequest.getEmail();
//        String password = userRequest.getPassword();
//        String passwordConfirm = userRequest.getPasswordConfirm();
//
//        if (userId == null || name == null || email == null || password == null
//                || userId.trim().isEmpty() || name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
//            model.addAttribute("errorMessage", "필수 항목을 모두 입력해주세요.");
//            return "signup";
//        }
//
//        if (!password.equals(passwordConfirm)) {
//            model.addAttribute("errorMessage", "비밀번호 확인이 일치하지 않습니다.");
//            return "signup";
//        }
//
//        if (!userService.idCheck(userId)) {
//            model.addAttribute("errorMessage", "이미 사용 중인 아이디입니다.");
//            return "signup";
//        }
//
//        if (userService.signUp(UserVO.builder()
//                .userID(userId)
//                .email(email)
//                .name(name)
//                .password(password)
//                .build()))
//            return "redirect:/user/loginView";
//
//        model.addAttribute("errorMessage", "회원가입에 실패했습니다.");
//        return "signup";
//    }
    @GetMapping("getIdView")
    public String getIdView(){
        return "getid";
    }
//    @PostMapping("getId")
//    public String getId(UserRequest userRequest, Model model){
//        String name = userRequest.getName();
//        String email = userRequest.getEmail();
//
//        if (name == null || email == null || name.trim().isEmpty() || email.trim().isEmpty()) {
//            model.addAttribute("errorMessage", "이름과 이메일을 입력해주세요.");
//            return "getid";
//        }
//
//        String userId = userService.findUserIdByNameAndEmail(UserVO.builder()
//                .name(name.trim())
//                .email(email.trim())
//                .build());
//
//        if (userId == null) {
//            model.addAttribute("errorMessage", "일치하는 회원 정보가 없습니다.");
//            return "getid";
//        }
//
//        model.addAttribute("userId", userId);
//        return "getid";
//
//    }
    @GetMapping("updatePwView")
    public String updatePwView(){
        return "updatepw";
    }
//    @PostMapping("updatePw")
//    public String updatePw(UserRequest userRequest, Model model){
//        String userId = userRequest.getUserID();
//        String email = userRequest.getEmail();
//        String newPassword = userRequest.getPassword();
//        String newPasswordConfirm = userRequest.getPasswordConfirm();
//
//        if (userId == null || email == null || newPassword == null || newPasswordConfirm == null
//                || userId.trim().isEmpty() || email.trim().isEmpty()
//                || newPassword.trim().isEmpty() || newPasswordConfirm.trim().isEmpty()) {
//            model.addAttribute("errorMessage", "필수 항목을 모두 입력해주세요.");
//            return "updatepw";
//        }
//
//        if (!newPassword.equals(newPasswordConfirm)) {
//            model.addAttribute("errorMessage", "새 비밀번호 확인이 일치하지 않습니다.");
//            return "updatepw";
//        }
//
//        boolean updated = userService.updatePassword(UserVO.builder()
//                .userID(userId)
//                .email(email)
//                .password(newPassword)
//                .build());
//
//        if (!updated) {
//            model.addAttribute("errorMessage", "아이디 또는 이메일이 일치하지 않습니다.");
//            return "updatepw";
//        }
//
//        return "redirect:/user/loginView";
//    }

}
