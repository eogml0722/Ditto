package com.ditto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .mvcMatchers("/").permitAll()
                .mvcMatchers("/css/**", "/js/**", "/members/**", "/img/**", "/extras/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                //
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("memberId")
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/");

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { //비밀번호를 암호화하여 저장
        return new BCryptPasswordEncoder();
    }



}
