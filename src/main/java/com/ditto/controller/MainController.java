package com.ditto.controller;

import com.ditto.dto.ItemSearchDTO;
import com.ditto.dto.MainItemDTO;
import com.ditto.entity.AskBoard;
import com.ditto.entity.Board;
import com.ditto.entity.Member;
import com.ditto.repository.AskBoardRepository;
import com.ditto.service.BoardService;
import com.ditto.service.ItemService;
import com.ditto.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final BoardService boardService;
    private final AskBoardRepository askBoardRepository;

    @GetMapping(value = "/")
    public String goMain(Model model, Principal principal, Optional<Integer> pageNum){

        // 공지사항 내역을 불러옴
        Pageable pageable = PageRequest.of((pageNum.isPresent() ? pageNum.get() -1 : 0 ), 5);
        Page<Board> boardPage = boardService.findAllByOrderByIdDesc(pageable);
        model.addAttribute("boardPage", boardPage);

        if(principal==null){
            return "index";
        } else {
            // 문의했던 내역을 불러옴
            List<AskBoard> askBoards = askBoardRepository.findAll();
            model.addAttribute("askBoards", askBoards);

            return "index";
        }

    }





}
