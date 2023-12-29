package com.ditto.controller;

import com.ditto.constant.BoardCategory;
import com.ditto.dto.BoardDTO;
import com.ditto.entity.Board;
import com.ditto.repository.BoardRepository;
import com.ditto.service.BoardService;
import com.ditto.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Case;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final ItemService itemService;


    @GetMapping({"/{pageNum}", "/", ""})//Optional = null 체크알아서 해줌
    public String goBoard(Model model, @PathVariable("pageNum") Optional<Integer> pageNum,
                          @RequestParam(value = "category")Optional<BoardCategory> boardCategory,
                          @RequestParam("keyword") Optional<String> keyword,
                          @RequestParam("option") Optional<String> option) {

        Pageable pageable = PageRequest.of((pageNum.isPresent() ? pageNum.get() -1 : 0 ), 5);
        Page<Board> boardPage = null;
        if(keyword.isPresent()) {
            switch (option.get()){
                case "title":
                    boardPage = boardService.findByTitle(pageable, keyword.get());
                    break;
                case "content":
                    boardPage = boardService.findByContent(pageable, keyword.get());
                    break;
                case "all":
                    boardPage = boardService.findBySearch(pageable, keyword.get());
                    break;
            }
        } else {
            if(boardCategory.isPresent()){
                boardPage = boardService.findByBoardCategory(pageable, boardCategory.get());
            } else {
                boardPage = boardService.findAllByOrderByIdDesc(pageable);
            }
        }


        model.addAttribute("boardPage", boardPage);
        model.addAttribute("maxPage", 10);

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




}
