package com.ditto.config;


import com.ditto.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private MemberService memberService;

    //5.7.0부터는 오버라이드 하지않고 SecurityFilterChain을 직접 생성하여 구현
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
                //모든 사용자가 인증없이 접근 가능
                .mvcMatchers("/", "/menu").permitAll()
                .mvcMatchers("/css/**", "/js/**", "/members/**", "/img/**", "/extras/**",
                                      "/check-email-token","/email-login", "/check-email-login", "/login-link", "/login-by-email", "/menu").permitAll()
                //admin으로 시작하는 경로는 ADMIN만 가능
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                //나머지는 모두 인증을 요청
                .mvcMatchers("/ask/**", "/cart/**", "/images/**").permitAll()
                .anyRequest().authenticated();

        //자동로그인
        http.rememberMe()
                .key("ditto") //인증토큰 생성시 사용할 키
                .tokenValiditySeconds(60*60*24*7) //인증 토큰 유효 시간(초) 1년으로 할경우 (3600*24*365) 로 설정
                .userDetailsService(memberService) //인증 토큰 생성시 사용할 userDetailService
                .rememberMeParameter("remember-me"); //로그인 페이지에서 사용할 파라미터 이름

        //인증받지 않은 사용자가 접근하면 수행
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        return http.build();
    }

    //AuthenticationManager : 스프링 시큐리티에서 인증을 처리하는 인터페이스
    //AuthenticationConfiguration : 스프링 시큐리티에서 인증 구성을 담당하는 클래스, 인증과 관련된 설정을 가져옴
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
