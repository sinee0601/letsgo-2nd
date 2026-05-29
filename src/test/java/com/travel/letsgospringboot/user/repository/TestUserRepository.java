package com.travel.letsgospringboot.user.repository;

import com.travel.letsgospringboot.user.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TestUserRepository {
    @Autowired
    UserRepository userRepository;

    @Test
    void login_correctCredentials_returnsUserVO() {
        UserVO user = userRepository.login(new UserVO("user01", null, null, "pass123"));
        assertNotNull(user);
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
    }

    @Test
    void login_wrongPassword_returnsNull() {
        UserVO user = userRepository.login(new UserVO("user03", null, null, "wrongPassword"));
        assertNull(user);
    }

    @Test
    void signUp_newUser_returnsTrue() {
        boolean result = userRepository.signUp(new UserVO("testUser99", "test@email.com", "테스터", "pw1234"));
        assertTrue(result);
    }

    @Test
    void idCheck_existingUser_returnsTrue() {
        assertTrue(userRepository.idCheck("user01"));
    }

    @Test
    void idCheck_nonexistentUser_returnsFalse() {
        assertFalse(userRepository.idCheck("noSuchUser9999"));
    }

    @Test
    void updatePassword_correctInfo_returnsTrue() {
        boolean result = userRepository.updatePassword(new UserVO("user03", "park@test.com", null, "newPass1234"));
        assertTrue(result);
    }

    @Test
    void updatePassword_wrongEmail_returnsFalse() {
        boolean result = userRepository.updatePassword(new UserVO("user03", "wrong@email.com", null, "newPass1234"));
        assertFalse(result);
    }

    @Test
    void findUserIdByNameAndEmail_correctInfo_returnsUserId() {
        String userId = userRepository.findUserIdByNameAndEmail(new UserVO(null, "kim@test.com", "김철수", null));
        assertNotNull(userId);
        assertEquals("user01", userId);
    }

    @Test
    void findUserIdByNameAndEmail_wrongInfo_returnsNull() {
        String userId = userRepository.findUserIdByNameAndEmail(new UserVO(null, "none@email.com", "없는사람", null));
        assertNull(userId);
    }
}
