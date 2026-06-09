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

        Map<String, Object> result = new HashMap<>();

        if (userId == null || name == null || email == null || password == null || passwordConfirm == null
                || userId.trim().isEmpty() || name.trim().isEmpty()
                || email.trim().isEmpty() || password.trim().isEmpty() || passwordConfirm.trim().isEmpty()) {
            result.put("result", "fail");
            result.put("message", "필수 항목을 모두 입력해주세요.");
            return ResponseEntity.badRequest().body(result);
        }

        if (!password.equals(passwordConfirm)) {
            result.put("result", "fail");
            result.put("message", "비밀번호 확인이 일치하지 않습니다.");
            return ResponseEntity.ok(result);
        }

        try {
            if (!userService.idCheck(userId)) {
                result.put("result", "fail");
                result.put("message", "이미 사용 중인 아이디입니다.");
                return ResponseEntity.ok(result);
            }

            boolean success = userService.signUp(UserVO.builder()
                    .userID(userId)
                    .email(email)
                    .name(name)
                    .password(password)
                    .build());

            if (!success) {
                result.put("result", "fail");
                result.put("message", "회원가입에 실패했습니다.");
                return ResponseEntity.ok(result);
            }

            result.put("result", "success");
            result.put("message", "회원가입 완료. 로그인 해주세요.");
            result.put("url", "/user/loginView");
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            result.put("result", "fail");
            result.put("message", "회원가입 중 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @PostMapping("/getIdAjax")
    public ResponseEntity<Map<String, Object>> getIdAjax(UserRequest userRequest) {
        String name = userRequest.getName();
        String email = userRequest.getEmail();

        Map<String, Object> result = new HashMap<>();

        if (name == null || email == null || name.trim().isEmpty() || email.trim().isEmpty()) {
            result.put("result", "fail");
            result.put("message","이름과 이메일을 입력해주세요.");
            return ResponseEntity.badRequest().body(result);
        }

        try {
            String userId = userService.findUserIdByNameAndEmail(UserVO.builder()
                    .name(name.trim())
                    .email(email.trim())
                    .build());

            if (userId == null) {
                result.put("result", "fail");
                result.put("message", "일치하는 회원 정보가 없습니다.");
                return ResponseEntity.ok(result);
            }

            result.put("result", "success");
            result.put("userId", userId);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            result.put("result", "fail");
            result.put("message", "조회 중 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @PostMapping("/updatePwAjax")
    public ResponseEntity<Map<String, Object>> updatePwAjax(UserRequest userRequest) {
        String userId = userRequest.getUserID();
        String email = userRequest.getEmail();
        String newPassword = userRequest.getPassword();
        String newPasswordConfirm = userRequest.getPasswordConfirm();

        Map<String, Object> result = new HashMap<>();

        if (userId == null || email == null || newPassword == null || newPasswordConfirm == null
                || userId.trim().isEmpty() || email.trim().isEmpty()
                || newPassword.trim().isEmpty() || newPasswordConfirm.trim().isEmpty()) {
            result.put("result", "fail");
            result.put("message", "필수 항목을 모두 입력해주세요.");
            return ResponseEntity.badRequest().body(result);
        }

        if (!newPassword.equals(newPasswordConfirm)) {
            result.put("result", "fail");
            result.put("message","새 비밀번호 확인이 일치하지 않습니다.");
            return ResponseEntity.ok(result);
        }

        try {
            boolean updated = userService.updatePassword(UserVO.builder()
                    .userID(userId)
                    .email(email)
                    .password(newPassword)
                    .build());

            if (!updated) {
                result.put("result", "fail");
                result.put("message", "아이디 또는 이메일이 일치하지 않습니다.");
                return ResponseEntity.ok(result);
            }

            result.put("result", "success");
            result.put("message", "비밀번호가 변경되었습니다. 로그인 해주세요.");
            result.put("url", "/user/loginView");
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            result.put("result", "fail");
            result.put("message", "비밀번호 변경 중 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(result);
        }
    }
}
