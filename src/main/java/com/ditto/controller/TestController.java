package com.ditto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// qna 관련 페이지 이동 테스트 컨트롤러
@Controller
@RequestMapping("/qna")
public class TestController {
    @GetMapping("/list")
    public String List(){
        return "qnaboard/qnaBoardList";
    }
    @GetMapping("/write")
    public String Write(){
        return "qnaboard/qnaBoardWrite";
    }

    @GetMapping("/detail")
    public String Detail(){
        return "qnaboard/qnaBoardDetail";
    }

    @GetMapping("/edit")
    public String Update(){
        return "qnaboard/qnaBoardEdit";
    }

    @GetMapping("/manage")
    public String ManageWrite(){
        return "qnaboard/qnaBoardMangerWrite";
    }


}
