package com.ditto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.formLogin()
                .loginPage("/members/login") //로그인 페이지 설정
                .defaultSuccessUrl("/") //로그인 성공시 이동할 페이지
                .usernameParameter("memberId") //로그인에 사용할 이름
                .failureUrl("/members/login/error") //로그인 실패시 이동할 페이지
                .and()
                .logout()//로그아웃 처리
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))//로그아웃을 실행할 주소
                .logoutSuccessUrl("/"); //로그아웃 성공시이동할 url

        http.authorizeRequests()
                .mvcMatchers("/").permitAll()
                .mvcMatchers("/css/**", "/js/**", "/members/**", "/img/**", "/extras/**").permitAll()
                .mvcMatchers("/ask/**", "/images/**").permitAll()
                .anyRequest().authenticated();

        //OAuth2기반의 로그인을 한 경우
//        http.oauth2Login()
//                //인증이 필요한 URL에 접근하면 /loginForm으로 이동
//                .loginPage("/members/login")
//                //로그인 성공 시 메인화면
//                .defaultSuccessUrl("/")
//                //로그인 실패 시 /loginForm으로 이동
//                .failureUrl("/members/login/error")
//                //로그인 성공 후 사용자 정보를 가져온다.
//                .userInfoEndpoint();

        //인증받지 않은 사용자가 접근하면 수행
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        // 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }



}
