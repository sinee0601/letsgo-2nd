package com.travel.letsgospringboot.user.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        //비로그인
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/**").permitAll()
                        .anyRequest().authenticated());

        //로그인
        http.formLogin(form
                        -> form.loginPage("/loginView")
                        .loginProcessingUrl("/login")
                        .usernameParameter("userID")
                        .passwordParameter("password")
                        .failureUrl("/loginView?error=true")
                        .defaultSuccessUrl("/user")
        );
        return http.build();
    }
}
