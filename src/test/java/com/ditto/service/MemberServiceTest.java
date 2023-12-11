package com.ditto.service;


import com.ditto.dto.MemberFormDTO;
import com.ditto.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    //테스트를 위한 멤버를 생성
    public Member createMember() {
        MemberFormDTO memberFormDTO = new MemberFormDTO();
        memberFormDTO.setMemberId("kikiblee");
        memberFormDTO.setPassword("Dbwn6349*");
        memberFormDTO.setName("이유주");
        memberFormDTO.setPhoneNum1(1234);
        memberFormDTO.setPhoneNum2(3333);
        memberFormDTO.setAddress("삼산동");
        memberFormDTO.setEmail("kikiblee@naver.com");
        return Member.createMember(memberFormDTO, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입테스트")
    public void saveMemberTest() {
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        //jUnitTest문법 (assertTrue, assertFalse, assertNull..)
        assertEquals(member.getMemberId(), savedMember.getMemberId());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getPhoneNum1(), savedMember.getPhoneNum1());
        assertEquals(member.getPhoneNum2(), savedMember.getPhoneNum2());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getEmail(), savedMember.getEmail());

    }

//    @Test
//    @DisplayName("중복 회원 가입 테스트")
//    public void saveDuplicateMemberTest(){
//        Member member1 = createMember();
//        Member member2 = createMember();
//        memberService.saveMember(member1);
//
//        try{
//            memberService.saveMember(member2);
//        } catch(IllegalStateException e) {
//            assertTrue(e instanceof IllegalStateException);
//            assertEquals("이미 가입된 회원입니다.", e.getMessage());
//        }
//    }
}
