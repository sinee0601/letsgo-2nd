package com.travel.letsgospringboot.user.auth;

import com.travel.letsgospringboot.user.repository.JpaUsers;
import com.travel.letsgospringboot.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        JpaUsers jpaUsersEntity = userJpaRepository.findByUserID(userID);
        if (jpaUsersEntity != null) {
            return new AppUserDetails(jpaUsersEntity);
        }
        throw new UsernameNotFoundException("없는 아이디: " + userID);
    }
}
