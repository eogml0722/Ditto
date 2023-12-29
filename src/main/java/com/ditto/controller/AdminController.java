package com.ditto.controller;


import com.ditto.dto.BoardDTO;
import com.ditto.entity.Board;
import com.ditto.repository.BoardRepository;
import com.ditto.service.BoardService;
import com.ditto.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @GetMapping(value = "/board/create")
    public String goCreateBoard(Model model) {
        model.addAttribute("boardDTO", new BoardDTO());
        return "/board/createBoardForm";
    }

    @GetMapping(value = "/board/create/{boardId}")
    public String updateBoard(Model model, @PathVariable("boardId") Long boardId) {
        //게시글 수정하기
        //아이디로 해당글 정보를 뷰로 전달

        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        BoardDTO boardDTO = BoardDTO.of(board);
        model.addAttribute("boardDTO", boardDTO);
        return "/board/createBoardForm";
    }

    @PostMapping(value = "/board/create")
    public String createBoard(@Valid BoardDTO boardDTO,
                              BindingResult bindingResult,
                              @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
                              Model model) {

        if(bindingResult.hasErrors()){
            if (boardDTO.getId() != null) {
                Board board = boardRepository.findById(boardDTO.getId()).orElseThrow(EntityNotFoundException::new);
                boardDTO = BoardDTO.of(board);
            }
            boardDTO.setTitle("제목을 입력하세요.");
            model.addAttribute("boardDTO", boardDTO);
            return "/board/createBoardForm";
        }

        try {
            if (boardDTO.getId() == null) {
                Board board = boardDTO.createBoard();
                boardService.insertBoard(board, itemImgFileList);
            } else {
                boardService.updateBoard(boardDTO, itemImgFileList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("글 등록 중 예외 발생");
            return "redirect:/board/createBoardForm";
        }
        return "redirect:/board/";
    }
}
