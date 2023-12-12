package com.ditto.controller;

import com.ditto.dto.BoardDTO;
import com.ditto.entity.Board;
import com.ditto.repository.BoardRepository;
import com.ditto.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.management.ValueExp;
import javax.swing.*;
import java.security.Principal;
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

    @GetMapping(value = "/getBoard/{boardId}")
    public String getBoard(@PathVariable("boardId") Long boardId , Model model) {
        //경로로 board의 id를 받아왔다.
        //받아온 id 와 일치하는 board.id 의 board를 dto로 보내준다.
        Board board = boardService.getBoard(boardId);

        BoardDTO boardDTO = BoardDTO.of(board);

        //이전행
        boardDTO.setBoardPrev(boardRepository.findLatestBoard(boardDTO.getId()));

        //다음행
//        boardDTO.setBoardNext(boardRepository.getNext(boardDTO.getId()));

        model.addAttribute("boardDTO", boardDTO);

        return "/board/getBoard";
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
