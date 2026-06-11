package com.travel.letsgospringboot.user.auth;

import com.travel.letsgospringboot.user.repository.JpaUsers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class AppUserDetails implements UserDetails {

    private JpaUsers jpaUsers;

    public AppUserDetails(JpaUsers jpaUsers) {
        this.jpaUsers = jpaUsers;
    }

    public JpaUsers getUser() {
        return jpaUsers;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return jpaUsers.getRole();
            }
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return jpaUsers.getPassword();
    }

    @Override
    public String getUsername() {
        return jpaUsers.getUserID();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return jpaUsers.isEnabled();
    }
}
