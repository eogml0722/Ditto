package com.ditto.controller;

import com.ditto.dto.MemberFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

//    private final MemberService memberService;
//    private final PasswordEncoder passwordEncoder;

    @GetMapping(value="/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "member/memberForm";
    }

//    @PostMapping(value="/new")
//    public String memberForm(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model) {
//        if(bindingResult.hasErrors()){
//            return "member/memberForm";
//        }
//        try {
//            Memeber member = new Member();
//
//        }
//    }
}
