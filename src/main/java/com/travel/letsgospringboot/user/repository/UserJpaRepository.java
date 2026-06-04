package com.travel.letsgospringboot.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    //로그인
    User findByUserID(String userID);
    //아이디 찾기
    User findByNameAndEmail(String name, String email);
}
