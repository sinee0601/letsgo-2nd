package com.travel.letsgospringboot.user.service;

import com.travel.letsgospringboot.user.repository.JpaUsers;
import com.travel.letsgospringboot.user.repository.UserJpaRepository;
import com.travel.letsgospringboot.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    public UserVO login(UserVO userVO) {
//        return userRepository.login(userVO);
//    }
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
        }
    }

    @Transactional
    public void suspendUser(String userID) {
        JpaUsers user = userJpaRepository.findByUserID(userID);
        if (user != null) {
            user.setEnabled(false);
            userJpaRepository.save(user);
        }
    }

    @Transactional
    public void unsuspendUser(String userID) {
        JpaUsers user = userJpaRepository.findByUserID(userID);
        if (user != null) {
            user.setEnabled(true);
            user.setWarningCount(0);
            userJpaRepository.save(user);
        }
    }

    public boolean signUp(UserVO userVO) {
        if (userJpaRepository.findByUserID(userVO.getUserID()) != null) {
            return false;
        }
        return (userJpaRepository.save(JpaUsers.builder()
                .userID(userVO.getUserID())
                .password(bCryptPasswordEncoder.encode(userVO.getPassword()))
                .email(userVO.getEmail())
                .name(userVO.getName())
                .role("ROLE_USER").build())) != null;
    }

    public boolean idCheck(String userID) {
        return userJpaRepository.findByUserID(userID) == null;
    }

    public boolean updatePassword(UserVO userVO) {
        JpaUsers jpaUsers = userJpaRepository.findByUserID(userVO.getUserID());
        if (jpaUsers == null || !jpaUsers.getEmail().equals(userVO.getEmail())) {
            return false;
        }
        return (userJpaRepository.save(JpaUsers.builder()
                .id(jpaUsers.getId())
                .userID(jpaUsers.getUserID())
                .password(bCryptPasswordEncoder.encode(userVO.getPassword()))
                .email(jpaUsers.getEmail())
                .name(jpaUsers.getName())
                .role(jpaUsers.getRole())
                .created(jpaUsers.getCreated())
                .build())) != null;
    }

    public String findUserIdByNameAndEmail(UserVO userVO) {
        JpaUsers jpaUsers = userJpaRepository.findByNameAndEmail(userVO.getName(), userVO.getEmail());
        return jpaUsers != null ? jpaUsers.getUserID() : null;
    }
}
