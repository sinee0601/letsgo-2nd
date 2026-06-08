package com.travel.letsgospringboot.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<JpaUsers, Long> {
    //로그인
    JpaUsers findByUserID(String userID);
    //아이디 찾기
    JpaUsers findByNameAndEmail(String name, String email);
}
