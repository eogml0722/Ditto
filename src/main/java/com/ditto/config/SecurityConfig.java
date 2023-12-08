package com.ditto.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //보안 설정 커스터마이징
//스프링부트에서 이미 default로 SecurityConfig를 등록하는데, @Bean 객체로 다시 주입하게 되면서
//둘중 하나만 선택하라는 오류가 나타나서 어노테이션 지정을 해둠.(오류해결용)
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER) //(의존성주입 오류해결용 어노테이션)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .mvcMatchers("/").permitAll()
                .mvcMatchers("/css/**", "/js/**", "/img/**", "/extras/**").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //HTTP 요청에 대한 보안을 설정

    }

    @Bean
    public PasswordEncoder passwordEncoder() { //비밀번호를 암호화하여 저장
        return new BCryptPasswordEncoder();
    }



}
