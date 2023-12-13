package com.ditto.controller;

import com.ditto.dto.BoardWriteDTO;
import com.ditto.dto.QBoardListDTO;
import com.ditto.dto.QBoardSearchDTO;
import com.ditto.entity.QABoard;
import com.ditto.entity.aQBoard;
import com.ditto.service.QBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

// qna 관련 페이지 이동 테스트 컨트롤러
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class TestController {
    private final QBoardService qBoardService;
    @GetMapping(value={"/list", "list/{page}"})
    public String List(QBoardSearchDTO qBoardSearchDTO, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,3);
        Page<aQBoard> QBoards = qBoardService.getQBoardList(qBoardSearchDTO, pageable);
        model.addAttribute("QBoards", QBoards);
        model.addAttribute("qBoardSearchDTO", qBoardSearchDTO);
        model.addAttribute("maxPage", 5);
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
        return "qnaboard/qnaBoardList";
    }

    @GetMapping("/detail/{boardId}")
    public String Detail(Model model){
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
