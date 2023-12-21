package com.ditto.controller;

import com.ditto.dto.MemberFormDTO;
import com.ditto.entity.Member;
import com.ditto.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc //MVC테스트 프레임워크
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberControllerTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    //테스트에서 사용할 회원을 생성
    public Member createMember(String memberId, String password) {
        MemberFormDTO memberFormDTO = new MemberFormDTO();
        memberFormDTO.setMemberId(memberId);
        memberFormDTO.setName("홍길동");
        memberFormDTO.setPhoneNum("010-2222-2222");
        memberFormDTO.setEmail("kikiblee@daum.net");
        memberFormDTO.setZipcode("22222");
        memberFormDTO.setStreetAddress("삼산로1");
        memberFormDTO.setDetailAddress("그린아파트");
        memberFormDTO.setPassword(password);
        Member member = Member.createMember(memberFormDTO, passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String memberId = "test1234";
        String password = "1234";
        this.createMember(memberId, password);

        //SpringMVC 테스트 프레임워크에서 제공하는 메서드
        //mockMvc.perform() : Httprequest를 모방하여 컨트롤러의 동작을 테스트하는데 사용됨
        mockMvc.perform(formLogin().userParameter("memberId")
                        .loginProcessingUrl("/members/login") //로그인 처리 url 설정
                        .user(memberId)    //로그인에 사용될 사용자의 아이디
                        .password(password))    //로그인에 사용될 사용자의 비밀번호
                .andExpect(SecurityMockMvcResultMatchers.authenticated()); //테스트를 검증
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception {
        String memberId = "test1234";
        String password = "1234";
        this.createMember(memberId, password);

        //SpringMVC 테스트 프레임워크에서 제공하는 메서드
        //mockMvc.perform() : Httprequest를 모방하여 컨트롤러의 동작을 테스트하는데 사용됨
        mockMvc.perform(formLogin().userParameter("memberId")
                        .loginProcessingUrl("/members/login") //로그인 처리 url 설정
                        .user(memberId).password("12345"))    //로그인에 사용될 사용자의 아이디, 패스워드
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated()); //테스트를 검증
    }


}

