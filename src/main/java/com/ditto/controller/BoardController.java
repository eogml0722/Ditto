package com.ditto.controller;

import com.ditto.dto.BoardDTO;
import com.ditto.entity.Board;
import com.ditto.repository.BoardRepository;
import com.ditto.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.management.ValueExp;
import javax.swing.*;
import java.util.List;

@Controller
@RequestMapping(value = "/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @GetMapping(value = "")
    public String goBoard(Model model){
        List<Board> boardList = boardRepository.findAll();
        model.addAttribute("boardList", boardList);
        return "/board/board";
    }

    @GetMapping(value = "/create")
    public String goCreateBoard(Model model){
        model.addAttribute("boardDTO", new BoardDTO());
        return "createBoardForm";
    }



    @PostMapping(value = "/create")
    public String createBoard(Model model){
        return null;
    }
}
