package com.ditto.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.management.ValueExp;
import javax.swing.*;

@Controller
@RequiredArgsConstructor
public class BoardController {

    @GetMapping(value = "/board")
    public String goBoard(){
        return "board/menu";
    }

//    @GetMapping(value = "/board/create")
//    public String goCreateBoard(Model model){
//        model.addAttribute("boardDTO", new BoardDTO());
//        return "board/createBoardForm";
//    }

    @PostMapping(value = "/board/create")
    public String createBoard(Model model){
        return null;
    }

}
