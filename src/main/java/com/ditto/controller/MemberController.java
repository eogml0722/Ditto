package com.ditto.controller;

import com.ditto.dto.MemberFormDTO;
import com.ditto.entity.Member;
import com.ditto.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j //로그를 찍기 위하여 사용된 어노테이션
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @GetMapping(value = "/login")
    public String loginMember(){
        return "member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/memberLoginForm";
    }

    @GetMapping(value="/new")
    public String memberForm(Model model) { //회원가입 페이지로 이동하는 메서드
        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "member/memberForm";
    }

    @PostMapping(value="/new")
    public String memberForm(MemberFormDTO memberFormDTO) {

        Member member = Member.createMember(memberFormDTO, passwordEncoder);
        memberService.saveMember(member);

        return "redirect:/";
    }

    //아이디,비밀번호 찾기
    @RequestMapping(value="/findLoginInfo")
    public String showFindLoginInfo() {
        return "member/findLoginInfo";
    }
    }
