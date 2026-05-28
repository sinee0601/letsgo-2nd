package com.travel.letsgospringboot.user.service;

import com.travel.letsgospringboot.user.repository.UserRepository;
import com.travel.letsgospringboot.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserVO login(UserVO userVO) {
        return userRepository.login(userVO);
    }

    public boolean signUp(UserVO userVO) {
        return userRepository.signUp(userVO);
    }

    public boolean idCheck(String userID) {
        return userRepository.idCheck(userID);
    }

    public boolean updatePassword(UserVO userVO) {
        return userRepository.updatePassword(userVO);
    }

    public String findUserIdByNameAndEmail(UserVO userVO) {
        return userRepository.findUserIdByNameAndEmail(userVO);
    }
}
