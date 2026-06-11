package com.travel.letsgospringboot.user.controller;

import com.travel.letsgospringboot.user.service.UserService;
import com.travel.letsgospringboot.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/api")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/signUpAjax")
    public ResponseEntity<Map<String, Object>> signUpAjax(UserRequest userRequest) {
        String userId = userRequest.getUserID();
        String name = userRequest.getName();
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();
        String passwordConfirm = userRequest.getPasswordConfirm();

        if (userId == null || name == null || email == null || password == null || passwordConfirm == null
                || userId.trim().isEmpty() || name.trim().isEmpty()
                || email.trim().isEmpty() || password.trim().isEmpty() || passwordConfirm.trim().isEmpty()) {
            throw new IllegalArgumentException("필수 항목을 모두 입력해주세요.");
        }

        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.");
        }

        if (!userService.idCheck(userId)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        if (!userService.signUp(UserVO.builder()
                .userID(userId)
                .email(email)
                .name(name)
                .password(password)
                .build())) {
            throw new RuntimeException("회원가입에 실패했습니다.");
        }

        return ResponseEntity.ok(Map.of(
                "result", "success",
                "message", "회원가입 완료. 로그인 해주세요.",
                "url", "/user/loginView"));
    }

    @PostMapping("/getIdAjax")
    public ResponseEntity<Map<String, Object>> getIdAjax(UserRequest userRequest) {
        String name = userRequest.getName();
        String email = userRequest.getEmail();

        if (name == null || email == null || name.trim().isEmpty() || email.trim().isEmpty()) {
            throw new IllegalArgumentException("이름과 이메일을 입력해주세요.");
        }

        String userId = userService.findUserIdByNameAndEmail(UserVO.builder()
                .name(name.trim())
                .email(email.trim())
                .build());

        if (userId == null) {
            throw new NullPointerException("일치하는 회원 정보가 없습니다.");
        }

        return ResponseEntity.ok(Map.of("result", "success", "userId", userId));
    }

    @PostMapping("/updatePwAjax")
    public ResponseEntity<Map<String, Object>> updatePwAjax(UserRequest userRequest) {
        String userId = userRequest.getUserID();
        String email = userRequest.getEmail();
        String newPassword = userRequest.getPassword();
        String newPasswordConfirm = userRequest.getPasswordConfirm();

        if (userId == null || email == null || newPassword == null || newPasswordConfirm == null
                || userId.trim().isEmpty() || email.trim().isEmpty()
                || newPassword.trim().isEmpty() || newPasswordConfirm.trim().isEmpty()) {
            throw new IllegalArgumentException("필수 항목을 모두 입력해주세요.");
        }

        if (!newPassword.equals(newPasswordConfirm)) {
            throw new IllegalArgumentException("새 비밀번호 확인이 일치하지 않습니다.");
        }

        boolean updated = userService.updatePassword(UserVO.builder()
                .userID(userId)
                .email(email)
                .password(newPassword)
                .build());

        if (!userService.updatePassword(UserVO.builder()
                .userID(userId)
                .email(email)
                .password(newPassword)
                .build())) {
            throw new IllegalArgumentException("아이디 또는 이메일이 일치하지 않습니다.");
        }

        return ResponseEntity.ok(Map.of(
                "result", "success",
                "message", "비밀번호가 변경되었습니다. 로그인 해주세요.",
                "url", "/user/loginView"));
    }
}
