package com.ditto.controller;

import com.ditto.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.management.ValueExp;
import javax.swing.*;

@Controller
@RequestMapping(value = "/board")
@RequiredArgsConstructor
public class BoardController {

    @GetMapping(value = "/")
    public String goBoard(){
        return "board/menu";
    }

    @GetMapping(value = "/create")
    public String goCreateBoard(Model model){
        model.addAttribute("boardDTO", new BoardDTO());
        return "board/createBoardForm";
    }



    @PostMapping(value = "/create")
    public String createBoard(Model model){
        return null;
    }
}
