package com.travel.letsgospringboot.user.service;

import com.travel.letsgospringboot.user.repository.User;
import com.travel.letsgospringboot.user.repository.UserJpaRepository;
import com.travel.letsgospringboot.user.repository.UserRepository;
import com.travel.letsgospringboot.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    public UserVO login(UserVO userVO) {
//        return userRepository.login(userVO);
//    }

    public boolean signUp(UserVO userVO) {
        if (userJpaRepository.findByUserID(userVO.getUserID()) != null) {
            return false;
        }
        return (userJpaRepository.save(User.builder()
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
        User user = userJpaRepository.findByUserID(userVO.getUserID());
        if (user == null) {
            return false;
        }
        return (userJpaRepository.save(User.builder()
                .id(user.getId())
                .userID(user.getUserID())
                .password(bCryptPasswordEncoder.encode(userVO.getPassword()))
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build())) != null;
    }

    public String findUserIdByNameAndEmail(UserVO userVO) {
        User user = userJpaRepository.findByNameAndEmail(userVO.getName(), userVO.getEmail());
        return user != null ? user.getUserID() : null;
    }
}
