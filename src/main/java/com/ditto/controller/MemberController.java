package com.ditto.controller;

import com.ditto.dto.MemberFormDTO;
import com.ditto.entity.Member;
import com.ditto.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

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

    @GetMapping(value="/mypage")
    public String myPage(Model model, Principal principal){
        String email = principal.getName();
        Member member = memberService.detailMember(principal.getName());
        String name = member.getName();model.addAttribute("name", name);
        model.addAttribute("memberFormDTO", member);
        return "member/MyPage";
    }

    @PostMapping(value="/update")
    public String memberUpdate(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "member/MyPage";
        }
        try{
            Member member = Member.createMember(memberFormDTO, passwordEncoder);
            memberService.updateMember(member);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/MyPage";
        }
        return "redirect:/";
    }

    @PostMapping(value="/pwupdate")
    public String passwordUpdate(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "member/MyPage";
        }
        try{
            MemberFormDTO memberFormDTO1 = memberService.updatePassword(memberFormDTO);
            Member member = Member.createMember(memberFormDTO1, passwordEncoder);
            memberService.updateMember(member);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/MyPage";
        }
        return "redirect:/";
    }

    @PostMapping(value="/delete")
    public String loginDelete(@RequestParam String password, Model model, Principal principal){
        boolean chk = memberService.deleteMember(principal.getName(), password);
        if(chk){
            model.addAttribute("alert", "삭제되었습니다");
            SecurityContextHolder.clearContext();
            return "redirect:/";
        } else  {
            model.addAttribute("errorMsg", "비밀번호가 일치하지않습니다.");
            return "member/memberDelete";
        }
    }


}
