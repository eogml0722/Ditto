package com.ditto.service;


import com.ditto.entity.Board;
import com.ditto.entity.Member;
import com.ditto.repository.BoardRepository;
import com.ditto.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    //게시글 생성
    public Board insertBoard(Board board){
        insertBoardDuplication(board);
        return boardRepository.save(board);
    }

    //게시글 생성 중복 검사 (저장하기 전에 검사)
    public void insertBoardDuplication(Board board){
        if(board.getId() != null){
            throw new IllegalArgumentException("중복된 게시글입니다.");}
    }

    //게시글 검색
    //id 는 principal이나 dto 로 교체?
    public List<Board> selectBoard(String id, String title, String content){
        //글 작성자 정보
        Member member = memberRepository.findByMemberId(id);

        //해당 회원이 작성한 글을 기준으로 검색
        return boardRepository.findByMember(member, title, content);
    }



}
