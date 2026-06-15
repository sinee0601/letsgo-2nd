package com.travel.letsgospringboot.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserJpaRepository extends JpaRepository<JpaUsers, Long> {
    //로그인
    JpaUsers findByUserID(String userID);
    //아이디 찾기
    JpaUsers findByNameAndEmail(String name, String email);

    JpaUsers findByEmail(String email);

    List<JpaUsers> findByUserIDContainingOrNameContaining(String userID, String name);

}
