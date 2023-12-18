package com.ditto.controller;

import com.ditto.dto.AskBoardSearchDTO;
import com.ditto.dto.BoardWriteDTO;
import com.ditto.entity.AskBoard;
import com.ditto.entity.Member;
import com.ditto.repository.MemberRepository;
import com.ditto.service.AskBoardService;
import com.ditto.service.BoardImageService;
import com.ditto.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ask")
public class askBoardController {

    private final AskBoardService askBoardService;
    private final MemberService memberService;
    private final BoardImageService boardImageService;

    @GetMapping(value={"/list", "/list/{page}"})
    public String AskBoardList(AskBoardSearchDTO askBoardSearchDTO, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<AskBoard> askBoards = askBoardService.getAskBoardList(askBoardSearchDTO, pageable);
        model.addAttribute("askBoards", askBoards);
        model.addAttribute("askBoardSearchDTO", askBoardSearchDTO);
        model.addAttribute("maxPage", 5);
        return "askBoard/askBoardList";
    }

    @GetMapping("/write")
    public String writeAskBoard(Model model){
        model.addAttribute("boardWriteDTO", new BoardWriteDTO());
        return "askBoard/askBoardWrite";
    }

    @PostMapping("/write")
    public String writeAskBoard(@Valid BoardWriteDTO boardWriteDTO, BindingResult bindingResult, Principal principal, Model model, @RequestParam("boardImgFile")List<MultipartFile> boardImgFileList){
        if(bindingResult.hasErrors()){
            return "askBoard/askBoardWrite";
        }
        try{
            Member member = memberService.detailMember(principal.getName());
            boardWriteDTO.setMember(member);
            askBoardService.writeBoard(boardWriteDTO, boardImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "게시글 등록중 에러가 발생");
            return "askBoard/askBoardList";
        }
        return "redirect:/ask/list";
    }

    @GetMapping("detail/{askboardId}")
    public String askBoardDetail(@PathVariable("askboardId") Long askBoardId, Model model, Principal principal){

        try{
            BoardWriteDTO boardWriteDTO = askBoardService.getAskBoardDetail(askBoardId);
            Member member = memberService.detailMember(principal.getName());
            boardWriteDTO.setMember(member);
            model.addAttribute("boardWriteDTO", boardWriteDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "게시글 상세보기중 에러가 발생");
            return "redirect:/";
        }
        return "askBoard/askBoardDetail";
    }

    @GetMapping("edit/{askboardId}")
    public  String askBoardUpdate(@PathVariable("askboardId") Long id, Model model){
        try{
            BoardWriteDTO boardWriteDTO = askBoardService.getAskBoardDetail(id);
            model.addAttribute("boardWriteDTO", boardWriteDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "없는 게시판입니다");
        }
        return "askBoard/askBoardEdit";
    }

    @PostMapping("edit/{askboardId}")
    public String askBoardUpdate(@PathVariable("askboardId") Long id, @Valid BoardWriteDTO boardWriteDTO, Principal principal, @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList, Model model) {
        System.out.println("contr : " + boardWriteDTO.toString());
        System.out.println("contr : " + boardImgFileList.toString());
        try {
            Member member = memberService.detailMember(principal.getName());
            BoardWriteDTO boardWriteDTO1 = askBoardService.getAskBoardDetail(id);
            boardWriteDTO1.setTitle(boardWriteDTO.getTitle());
            boardWriteDTO1.setContent(boardWriteDTO.getContent());
            System.out.println("contr " + boardImgFileList);
            askBoardService.updateAskBoard(boardWriteDTO1, boardImgFileList);
            model.addAttribute("Message", "수정되었습니다!");
            if (member.getName().equals(askBoardService.getAskBoardDetail(id).getMember().getName())) {
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "게시글 수정중 에러");
            return "askBoard/askBoardEdit";
        }
        return "redirect:/ask/list";
    }

    @PostMapping("/delete/{id}")
    public String Delete(@PathVariable("id") Long id, Principal principal, Model model){
        Member member = memberService.detailMember(principal.getName());
        if (member.getName().equals(askBoardService.getAskBoardDetail(id).getMember().getName())) {
            askBoardService.deleteAskBoard(id);
        } else {
            model.addAttribute("errorMessage", "본인게시글만 삭제가능");
            return "redirect:/ask/list";
        }
        model.addAttribute("Message", "삭제되었습니다!");
        return "redirect:/ask/list";
    }

}
