package com.travel.letsgospringboot.user.auth;

import com.travel.letsgospringboot.user.repository.User;
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
        User userEntity = userJpaRepository.findByUserID(userID);
        if (userEntity != null) {
            return new AppUserDetails(userEntity);
        }
        throw new UsernameNotFoundException("없는 아이디: " + userID);
    }
}
