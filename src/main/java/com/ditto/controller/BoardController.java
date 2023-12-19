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
import javax.persistence.EntityNotFoundException;
import javax.persistence.Id;
import javax.swing.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @GetMapping(value = {"/{pageNum}", "/"})//Optional = null 체크알아서 해줌
    public String goBoard(Model model, @PathVariable("pageNum") Optional<Integer> pageNum) {
        List<Board> boardList = boardRepository.findAll();

        //maxPage : 페이지 최대 갯수
        final Integer maxPage = 10;

        if (!pageNum.isEmpty()) {
            System.out.println("번호" + pageNum.get());
            model.addAttribute("pageNum", pageNum.get());
        } else {
            model.addAttribute("pageNum", new Integer(1));
        }
        model.addAttribute("boardList", boardList);
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("totalPage", (int) (Math.ceil((double) boardList.size() / 10)));
        return "/board/board";
    }

    @GetMapping(value = "/getBoard/{boardId}")
    public String getBoard(@PathVariable("boardId") Long boardId, Model model) {
        //경로로 board의 id를 받아왔다.
        //받아온 id 와 일치하는 board.id 의 board를 dto로 보내준다.
        Board board = boardService.getBoard(boardId);

        BoardDTO boardDTO = BoardDTO.of(board);

        //이전행
//        boardDTO.setBoardPrev(boardRepository.findLatestBoard(boardDTO.getId()));

        //다음행
//        boardDTO.setBoardNext(boardRepository.getNext(boardDTO.getId()));

        model.addAttribute("boardDTO", boardDTO);

        return "/board/getBoard";
    }

    //글삭제
    @PostMapping(value = "/getBoard/{boardId}")
    public String deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/board/";
    }


    @GetMapping(value = "/create")
    public String goCreateBoard(Model model) {
        model.addAttribute("boardDTO", new BoardDTO());
        return "/board/createBoardForm";
    }

    @GetMapping(value = "/create/{boardId}")
    public String updateBoard(Model model, @PathVariable("boardId") Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        BoardDTO boardDTO = BoardDTO.of(board);
        model.addAttribute("boardDTO", boardDTO);
        return "/board/createBoardForm";
    }

    @PostMapping(value = "/create")
    public String createBoard(BoardDTO boardDTO) {
        try {
            if (boardDTO.getId() == null) {
                Board board = boardDTO.createBoard();
                boardService.insertBoard(board);
            } else {
                boardService.updateBoard(boardDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("글 등록 중 예외 발생");
            return "redirect:/board/createBoardForm";
        }
        return "redirect:/board/";
    }
}
