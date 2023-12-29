package com.ditto.controller;

import com.ditto.constant.Role;
import com.ditto.dto.MemberFormDTO;
import com.ditto.dto.OrderDTO;
import com.ditto.entity.AskBoard;
import com.ditto.entity.Member;
import com.ditto.repository.AskBoardRepository;
import com.ditto.repository.MemberRepository;
import com.ditto.service.MemberService;
import com.ditto.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j //로그를 찍기 위하여 사용된 어노테이션
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;
    private final AskBoardRepository askBoardRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;
    private final OrderService orderService;

    //로그인
    @GetMapping(value = "/login")
    public String loginMember() {
        return "member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/memberLoginForm";
    }

    //회원가입
    @GetMapping(value = "/new")
    public String memberForm(Model model) { //회원가입 페이지로 이동하는 메서드
        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String memberForm(MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }
        try {
            Member member = Member.createMember(memberFormDTO, passwordEncoder);
            memberService.saveMember(member);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/findLoginInfo")
    public String findId() {
        return "member/findLoginInfo";
    }


    @PostMapping("/findLoginInfo")
    public String findAccountAndPassword(@RequestParam Map<String, Object> params, Model model, RedirectAttributes rttr, Locale locale) {
        log.info("params : {}", params);
        String name = params.get("name").toString();
        String email = params.get("email").toString();
        if (params.size() == 3) {
            /* 아이디 찾기(이름, 이메일) */
            log.info("find id 요청");
            Member resultId = memberService.findId(name, email);
            System.out.println(resultId);
            if (resultId == null) {
                rttr.addFlashAttribute("forgotIdMessage", "입력하신 정보와 일치하는 회원이 없습니다.");
                return "redirect:/members/findLoginInfo";
            } else {

                rttr.addFlashAttribute("forgotIdMessage", resultId.getMemberId());
            }
        }
        return "redirect:/members/login";
    }

    @GetMapping(value="/mypage")
    public String myPage(Model model, Principal principal, Optional<Integer> page){
        // 회원정보를 불러옴
        String email = principal.getName();
        Member member = memberService.detailMember(principal.getName());
        String name = member.getName();model.addAttribute("name", name);
        model.addAttribute("memberFormDTO", member);
        // 문의했던 내역을 불러옴
        ArrayList<AskBoard> askBoards = askBoardRepository.findByMemberOrderByIdDesc(member);
        model.addAttribute("askBoards", askBoards);
        // 주문했던 내역을 불러옴
        Pageable pageable = PageRequest.of(page.map(integer -> integer - 1).orElse(0), 5);
        Page<OrderDTO> orderDTOPage = orderService.findByMemberId(principal.getName(), pageable);
        model.addAttribute("orderDTOPage", orderDTOPage);

        return "member/MyPage";
    }

    @GetMapping("/email-login")
    public String emailLoginForm() { // 이메일 로그인뷰 페이지로 라우팅
        return "member/email-login";
    }

    @PostMapping("/email-login")
    public String sendLinkForEmailLogin(String email, Model model, RedirectAttributes attributes) { // 이메일 폼을 통해 입력받은 정보로 계정을 찾아 메일을 전송하고 다시 리다이렉트
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            model.addAttribute("emailError", "실패!");
            return "member/email-login";
        }
        if (!member.enableToSendEmail()) {
            model.addAttribute("tryError", "실패!");
            return "member/email-login";
        }
        memberService.sendLoginLink(member);
        attributes.addFlashAttribute("successMsg", "성공!");
        return "redirect:/members/email-login";
    }

    @GetMapping("/login-by-email")
    public String loginByEmail(String token, String email, Model model,RedirectAttributes rttr) { //링크를 통해 전달한 토큰과 이메일정보를 가지고 토큰의유효성을 판단후 로그인을 수행
        Member member = memberRepository.findByEmail(email);
        if (member == null || !member.isValid(token)) {
            rttr.addFlashAttribute("error", "로그인 실패");
            return "member/logged-in-by-email";
        } else {
            rttr.addFlashAttribute("error", "로그인 성공");
        }
        memberService.login(member);
//        return "member/logged-in-by-email";
        return "redirect:/";
    }

    @PostMapping(value="/update")
    public String memberUpdate(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Principal principal, Model model){
        if(bindingResult.hasErrors()){
            return "member/MyPage";
        }
        try{
            memberService.updateMember(memberFormDTO, principal);

            model.addAttribute("message", "회원정보가 수정되었습니다.");
            model.addAttribute("url", "/members/mypage");

            return "/fragments/alert";
        } catch (Exception e) {
            model.addAttribute("message", "정보변경중 오류가 발생하였습니다.");
            model.addAttribute("url", "/members/mypage");
            return "/fragments/alert";
        }
    }

    @PostMapping(value="/pwupdate")
    public String passwordUpdate(@RequestParam String password, @RequestParam String oldPassword, Principal principal, Model model){

        if(memberService.updatePassword(principal, password, oldPassword) != null) {
            memberService.updatePassword(principal, password, oldPassword);
            model.addAttribute("message", "비밀번호가 변경되었습니다 다시 로그인해주세요!");
            model.addAttribute("url", "/members/login");
            SecurityContextHolder.clearContext();
        } else {
            model.addAttribute("message", "현재 비밀번호를 다시 확인해주세요");
            model.addAttribute("url", "/members/mypage");
        }
        return "/fragments/alert";

    }

    @PostMapping(value="/delete")
    public String MemberDelete(@RequestParam String password, Model model, Principal principal){
        boolean chk = memberService.deleteMember(principal.getName(), password);
        if(chk){
            model.addAttribute("message", "회원탈퇴가 완료되었습니다.");
            model.addAttribute("url", "/");
            SecurityContextHolder.clearContext();
            return "/fragments/alert";
        } else  {
            model.addAttribute("message", "비밀번호가 일치하지않습니다.");
            model.addAttribute("url", "/members/mypage");
            return "/fragments/alert";
        }
    }

    @GetMapping(value="/manage")
    public String memberManage(@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable, Model model, Principal principal){
        Member member = memberService.detailMember(principal.getName());
        boolean admin = member.getRole() == Role.ADMIN;
        if(!admin){
            model.addAttribute("message", "관리자만 이용할 수 있습니다.");
            model.addAttribute("url", "/");
            return "/fragments/alert";
        }
//        model.addAttribute("memberFormDTO", new MemberFormDTO());
        model.addAttribute("members", memberRepository.findAll(pageable));
        return "member/memberMng";
    }

    @PostMapping(value="/deleteMng")
    public String memberManageDelete(@RequestParam String memberId, Model model, Principal principal){
        Member member = memberService.detailMember(principal.getName());
        boolean admin = member.getRole() == Role.ADMIN;
        if(!admin){
            model.addAttribute("message", "관리자만 이용할 수 있습니다.");
            model.addAttribute("url", "/");
            return "/fragments/alert";
        } else if (memberId.equals(principal.getName())) {
            model.addAttribute("message", "현재 로그인된 매니저 계정은 삭제할 수 없습니다.");
            model.addAttribute("url", "/members/manage");
            return "/fragments/alert";
        }
        memberService.deleteMemberManage(memberId);
        model.addAttribute("message", "삭제되었습니다.");
        model.addAttribute("url", "/members/manage");
        return "/fragments/alert";
    }

    @PostMapping(value="/updateMng")
    public String memberRoleUpdate(@RequestParam String memberId, Model model, Principal principal){
        Member member = memberService.detailMember(principal.getName());
        boolean admin = member.getRole() == Role.ADMIN;
        if(!admin){
            model.addAttribute("message", "관리자만 이용할 수 있습니다.");
            model.addAttribute("url", "/");
            return "/fragments/alert";
        }
        memberService.roleChange(memberId);
        model.addAttribute("message", "변경되었습니다.");
        model.addAttribute("url", "/members/manage");
        return "/fragments/alert";
    }


}
