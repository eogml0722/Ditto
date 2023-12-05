package com.ditto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.management.ValueExp;

@Controller
@RequiredArgsConstructor
public class BoardController {

    @GetMapping(value = "/board")
    public String goBoard(){
        return "menu";
    }
}
