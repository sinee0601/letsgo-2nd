package com.travel.letsgospringboot.user.repository;

import com.travel.letsgospringboot.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapper
public interface UserRepository {
    //UserVO login(UserVO userVO);

    boolean signUp(UserVO userVO);

    boolean idCheck(String userID);

    boolean updatePassword(UserVO userVO);

    String findUserIdByNameAndEmail(UserVO userVO);
}
