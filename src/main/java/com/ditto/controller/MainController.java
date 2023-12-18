package com.ditto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MainController {

//    @RequestMapping(value="/findLoginInfo", method = RequestMethod.POST)
//    @ResponseBody
//    public String findPwd(String mem_email, String mem_id, String inputCode, HttpSession session) throws Exception {
//            String keyCode = (String) session.getAttribute("keyCode");
//
//            if(!inputCode.equals(keyCode)) {
//                return "discordance";
//            }
//
//            session.removeAttribute("keyCode");
//
//            String newPwd = FindUtil.getNewPwd();
//
//
//            keyCode = FindUtil.createKey();
//            session.setAttribute("keyCode", keyCode);
//
//            String subject = "[Ditto] 비밀번호 찾기 인증코드 안내";
//
//            String msg = "";
//            msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
//            msg += "<div style='color: blue;'>비밀번호 찾기 인증코드입니다.</h3>";
//            msg += "비밀번호 찾기 페이지로 돌아가 인증코드 <strong>";
//            msg += keyCode + "</strong> 를 입력해주세요.</div><br/>";
//
//            MailUtil.sendMail(mem_email, subject, msg);
//
//    }
//
//    private final EmailService emailService;

    @GetMapping(value = "/")
    public String goMain(Model model){
        return "index";
    }


}
