package com.travel.letsgospringboot.user.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf
                .disable()
        );
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/", "/user/loginView", "/user/signUpView", "/user/signUp",
                                "/user/getIdView", "/user/getId",
                                "/user/updatePwView", "/user/updatePw",
                                "/user/api/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/myschedule/**").authenticated()
                        .requestMatchers("/postschedule/detail/**").authenticated()
                        .anyRequest().permitAll());

        http.formLogin(form
                -> form.loginPage("/user/loginView")
                .loginProcessingUrl("/login")
                .usernameParameter("userID")
                .passwordParameter("password")
                .failureHandler((request, response, exception) -> {
                    String errorType = "true";
                    if (exception instanceof DisabledException) {
                        errorType = "disabled";
                    }
                    response.sendRedirect("/user/loginView?error=" + errorType);
                })
                .successHandler((request, response, authentication) -> {
                    boolean isAdmin = authentication.getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                    if (isAdmin) {
                        response.sendRedirect("/admin");
                    } else {
                        response.sendRedirect("/");
                    }
                })
                .permitAll()
        );

        http.logout(logout
                -> logout.logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        );

        http.sessionManagement(session -> session
                .sessionFixation(sf -> sf.changeSessionId())
                .maximumSessions(15)
                .maxSessionsPreventsLogin(false)
        );



        return http.build();


    }
}
