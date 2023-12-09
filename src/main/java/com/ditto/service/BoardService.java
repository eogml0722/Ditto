package com.ditto.service;


import com.ditto.entity.Board;
import com.ditto.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

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

}
