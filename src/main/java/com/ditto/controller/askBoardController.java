package com.ditto.controller;

import com.ditto.constant.Role;
import com.ditto.dto.AskBoardSearchDTO;
import com.ditto.dto.BoardWriteDTO;
import com.ditto.dto.CommentWriteDTO;
import com.ditto.entity.AskBoard;
import com.ditto.entity.Member;
import com.ditto.service.AskBoardService;
import com.ditto.service.BoardImageService;
import com.ditto.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.xml.stream.events.Comment;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ask")
public class askBoardController {

    private final AskBoardService askBoardService;
    private final MemberService memberService;
    private final BoardImageService boardImageService;

    @GetMapping(value="/list")
    public String page0(){
        return "redirect:/ask/list/0";
    }

    @GetMapping("/list/{page}")
    public String AskBoardList(AskBoardSearchDTO askBoardSearchDTO, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
        Page<AskBoard> askBoards = askBoardService.getAskBoardList(askBoardSearchDTO, pageable);

        int now= page.get();
        int max = (int) askBoards.getTotalElements();

        if(now == 0){
            now = 0;
        } else if (now == 1){
            now = 4;
        } else if (now == 2){
            now = 8;
        } else if (now == 3){
            now = 12;
        }

        model.addAttribute("now", now);
        model.addAttribute("max", max);
        model.addAttribute("askBoards", askBoards);
        model.addAttribute("askBoardSearchDTO", askBoardSearchDTO);
        model.addAttribute("maxPage", 4);
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

            return "errorMessage";
        }
        return "redirect:/ask/list";
    }

    @GetMapping("detail/{askboardId}")
    public String askBoardDetail(@PathVariable("askboardId") Long askBoardId, Model model){
        try{
            BoardWriteDTO boardWriteDTO = askBoardService.getAskBoardDetail(askBoardId);
            Member member = boardWriteDTO.getMember();
            boardWriteDTO.setMember(member);
            model.addAttribute("boardWriteDTO", boardWriteDTO);
            model.addAttribute("commentWriteDTO", new CommentWriteDTO());

            CommentWriteDTO commentWriteDTO = askBoardService.getCommentDetail(askBoardId);
            model.addAttribute("commentDetail", commentWriteDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "게시글 상세보기중 에러가 발생");
            return "redirect:/";
        }
        return "askBoard/askBoardDetail";
    }

    @GetMapping("edit/{askboardId}")
    public  String askBoardUpdate(@PathVariable("askboardId") Long id, Model model, Principal principal){

        if(!askBoardService.validateAskBoard(id, principal.getName())){
            model.addAttribute("errorMessage", "같은 작성자만 수정할 수 있습니다");
            return "redirect:/ask/list";
        }

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
        try {
            Member member = memberService.detailMember(principal.getName());
            BoardWriteDTO boardWriteDTO1 = askBoardService.getAskBoardDetail(id);
            boardWriteDTO1.setTitle(boardWriteDTO.getTitle());
            boardWriteDTO1.setContent(boardWriteDTO.getContent());

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
        if ("ADMIN".equals(member.getRole().toString())) {
            askBoardService.deleteAskBoard(id);
            model.addAttribute("Message", "삭제되었습니다!");
            return "redirect:/ask/list";
        }

        if(!askBoardService.validateAskBoard(id, principal.getName())){
            model.addAttribute("errorMessage", "같은 작성자만 수정할 수 있습니다");
            return "redirect:/ask/list";
        } else {
            askBoardService.deleteAskBoard(id);
            model.addAttribute("Message", "삭제되었습니다!");
            return "redirect:/ask/list";
        }
    }

    @PostMapping("admin/write/{askboardId}")
    public String writeAskComment(@PathVariable("askboardId") Long id, @Valid CommentWriteDTO commentWriteDTO, BindingResult bindingResult, Principal principal, Model model, @RequestParam("boardImgFile")List<MultipartFile> boardImgFileList){
        if(bindingResult.hasErrors()){
            return "askBoard/askBoardList";
        }
        try{
            Member member = memberService.detailMember(principal.getName());
            commentWriteDTO.setMember(member);
            if ("ADMIN".equals(member.getRole().toString())) {
                askBoardService.writeComment(id, commentWriteDTO, boardImgFileList);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "답변 등록중 에러가 발생");
            return "askBoard/askBoardList";
        }
        return "redirect:/ask/list";
    }

    @GetMapping("/admin/edit/{askboardId}")
    public String editComment(@PathVariable("askboardId") Long id, Model model, Principal principal){
        try{
            // 원본 보드 게시글  DTO
            BoardWriteDTO boardWriteDTO = askBoardService.getAskBoardDetail(id);
            Member member = memberService.detailMember(principal.getName());
            boardWriteDTO.setMember(member);
            model.addAttribute("boardWriteDTO", boardWriteDTO);
            // 수정할 코멘트 DTO
            CommentWriteDTO commentWriteDTO = askBoardService.getCommentDetail(id);
            model.addAttribute("commentWriteDTO", commentWriteDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사이트 이동중 오류가 발생하였습니다.");
        }
        return "askBoard/askCommentEdit";
    }

    @PostMapping("/admin/edit/{askboardId}")
    public String editComment(@PathVariable("askboardId") Long id, @Valid CommentWriteDTO commentWriteDTO, Principal principal, @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList, Model model) {
        Member member = memberService.detailMember(principal.getName());
        try {
            CommentWriteDTO commentWriteDTO1 = askBoardService.getCommentDetail(id);
            commentWriteDTO1.setMember(member);
            commentWriteDTO1.setContent(commentWriteDTO.getContent());
            if ("ADMIN".equals(member.getRole().toString())) {
                model.addAttribute("errorMessage", "관리자만 수정가능");
                model.addAttribute("Message", "수정되었습니다!");
                askBoardService.updateComment(commentWriteDTO1, boardImgFileList);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "답변 수정중 에러");
            return "askBoard/askCommentEdit";
        }
        return "redirect:/ask/list";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteComment(@PathVariable("id") Long id, Principal principal, Model model){
        Member member = memberService.detailMember(principal.getName());
        if ("ADMIN".equals(member.getRole().toString())) {
            askBoardService.deleteComment(id);
        } else {
            model.addAttribute("errorMessage", "관리자만 삭제가능");
            return "redirect:/ask/list";
        }
        model.addAttribute("Message", "삭제되었습니다!");
        return "redirect:/ask/list";
    }

}
