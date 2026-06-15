package com.travel.letsgospringboot.user.service;

import com.travel.letsgospringboot.user.repository.JpaUsers;
import com.travel.letsgospringboot.user.repository.UserJpaRepository;
import com.travel.letsgospringboot.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<JpaUsers>getAllUsers(){
        return userJpaRepository.findAll();
    }

    public List<JpaUsers> searchUsers(String keyword){
        return userJpaRepository.findByUserIDContainingOrNameContaining(keyword, keyword);
    }

    @Transactional
    public void giveWarning(String userID, String reason) {
        JpaUsers user = userJpaRepository.findByUserID(userID);
        if (user != null) {
            int nextWarningCount = user.getWarningCount() + 1;
            user.setWarningCount(nextWarningCount);
            if (nextWarningCount >= 3) {
                user.setEnabled(false);
            }
            userJpaRepository.save(user);
            log.info("사용자 경고 부여 성공: userID={}, warningCount={}, enabled={}", userID, nextWarningCount, user.isEnabled());
        }
    }

    @Transactional
    public void suspendUser(String userID) {
        JpaUsers user = userJpaRepository.findByUserID(userID);
        if (user != null) {
            user.setEnabled(false);
            userJpaRepository.save(user);
            log.info("사용자 계정 정지 성공: userID={}", userID);
        }
    }

    @Transactional
    public void unsuspendUser(String userID) {
        JpaUsers user = userJpaRepository.findByUserID(userID);
        if (user != null) {
            user.setEnabled(true);
            user.setWarningCount(0);
            userJpaRepository.save(user);
            log.info("사용자 계정 정지 해제 성공: userID={}", userID);
        }
    }

    public boolean signUp(UserVO userVO) {
        if (userJpaRepository.findByUserID(userVO.getUserID()) != null) {
            return false;
        }
        if (userJpaRepository.findByEmail(userVO.getEmail()) != null) {
            return false;
        }
        boolean success = (userJpaRepository.save(JpaUsers.builder()
                .userID(userVO.getUserID())
                .password(bCryptPasswordEncoder.encode(userVO.getPassword()))
                .email(userVO.getEmail())
                .name(userVO.getName())
                .role("ROLE_USER").build())) != null;
        if (success) {
            log.info("사용자 회원가입 성공: userID={}", userVO.getUserID());
        }
        return success;
    }

    public boolean idCheck(String userID) {
        return userJpaRepository.findByUserID(userID) == null;
    }

    public boolean updatePassword(UserVO userVO) {
        JpaUsers jpaUsers = userJpaRepository.findByUserID(userVO.getUserID());
        if (jpaUsers == null || !jpaUsers.getEmail().equals(userVO.getEmail())) {
            return false;
        }
        boolean success = (userJpaRepository.save(JpaUsers.builder()
                .id(jpaUsers.getId())
                .userID(jpaUsers.getUserID())
                .password(bCryptPasswordEncoder.encode(userVO.getPassword()))
                .email(jpaUsers.getEmail())
                .name(jpaUsers.getName())
                .role(jpaUsers.getRole())
                .created(jpaUsers.getCreated())
                .warningCount(jpaUsers.getWarningCount())
                .enabled(jpaUsers.isEnabled())
                .build())) != null;
        if (success) {
            log.info("사용자 비밀번호 변경 성공: userID={}", userVO.getUserID());
        }
        return success;
    }

    public String findUserIdByNameAndEmail(UserVO userVO) {
        JpaUsers jpaUsers = userJpaRepository.findByNameAndEmail(userVO.getName(), userVO.getEmail());
        return jpaUsers != null ? jpaUsers.getUserID() : null;
    }

    public boolean emailCheck(String email) {
        return userJpaRepository.findByEmail(email) == null;
    }
}
