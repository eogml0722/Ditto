package com.ditto.controller;

import com.ditto.constant.BoardCategory;
import com.ditto.dto.BoardDTO;
import com.ditto.dto.BoardFormDTO;
import com.ditto.dto.ItemFormDTO;
import com.ditto.dto.OrderDTO;
import com.ditto.entity.Board;
import com.ditto.repository.BoardRepository;
import com.ditto.service.BoardService;
import com.ditto.service.ImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.ValueExp;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Id;
import javax.swing.*;
import javax.validation.Valid;
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
    private final ImgService imgService;


    @GetMapping({"/{pageNum}", "/", ""})//Optional = null 체크알아서 해줌
    public String goBoard(Model model, @PathVariable("pageNum") Optional<Integer> pageNum,
                          @RequestParam(value = "category")Optional<BoardCategory> boardCategory) {
//        if( boardFormDTO.getSearchField() != null){
//            boardList = boardRepository.findBySearch(boardFormDTO.getSearchField(),boardFormDTO.getSearchOption(),boardFormDTO.getBoardCategory());
//        }

        Pageable pageable = PageRequest.of((pageNum.isPresent() ? pageNum.get() -1 : 0 ), 5);
        Page<Board> boardPage;

        if(boardCategory.isPresent()){
            boardPage = boardService.findByBoardCategory(pageable, boardCategory.get());
        } else {
           boardPage = boardService.findAllByOrderByIdDesc(pageable);
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


    @GetMapping(value = "/create")
    public String goCreateBoard(Model model) {
        model.addAttribute("boardDTO", new BoardDTO());
        return "/board/createBoardForm";
    }

    @GetMapping(value = "/create/{boardId}")
    public String updateBoard(Model model, @PathVariable("boardId") Long boardId) {
        //아이디로 해당글 정보를 뷰로 전달
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

    @PostMapping(value="/create")
    public String itemNew(@Valid BoardDTO boardDTO,
                          BindingResult bindingResult,
                          Model model,
                          @RequestParam("imgFile") List<MultipartFile> imgFileList) {

        //유효성검사에서 오류가 있으면 상품 등록폼으로 이동
        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        //첫번째 이미지가 비어있고, 상품의 id가 없으면
        if(itemImgFileList.get(0).isEmpty() && boardDTO.getId() == null) {
            //에러메시지를 설정하고 상품등록폼으로 리턴
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수입니다.");
            return "item/itemForm";
        }

        try{
            //상품 저장
            imgService.saveItemImg(boardDTO, imgFileList);
        } catch (Exception e) {
            //예외 발생하면 에러메시지를 전달하고 상품등록폼으로 이동
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }
        //상품등록이 정상적으로 처리되었으면 메인페이지 이동
        return "redirect:/";
    }


}
