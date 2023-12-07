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

    public void insertBoard(Board board){
        boardRepository.save(board);
    }

}
