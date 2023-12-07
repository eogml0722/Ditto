package com.ditto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    @GetMapping(value="login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 비밀번호를 확인해주세요");
        return "/";
    }
}
