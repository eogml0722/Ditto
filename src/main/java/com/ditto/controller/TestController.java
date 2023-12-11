package com.ditto.controller;

import com.ditto.dto.BoardWriteDTO;
import com.ditto.service.QBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

// qna 관련 페이지 이동 테스트 컨트롤러
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class TestController {
    private final QBoardService qBoardService;
    @GetMapping("/list")
    public String List(){
        return "qnaboard/qnaBoardList";
    }
    @GetMapping("/write")
    public String Write(Model model){
        model.addAttribute("boardWriteDTO", new BoardWriteDTO());
        return "qnaboard/qnaBoardWrite";
    }

    @PostMapping("/write")
    public String Write(@Valid BoardWriteDTO boardWriteDTO, BindingResult bindingResult, Model model, @RequestParam("boardImgFile")List<MultipartFile> boardImgFileList) {
        if(bindingResult.hasErrors()){
            return"qnaboard/qnaBoardWrite";
        }
        try {
            qBoardService.writeBoard(boardWriteDTO, boardImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "문의 등록 중 에러가 발생하였습니다");
            return "qnaboard/qnaBoardWrite";
        }
        return "/qnaboard/qnaBoardList";
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
