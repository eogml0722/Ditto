package com.ditto.service;


import com.ditto.dto.BoardDTO;
import com.ditto.entity.Board;
import com.ditto.entity.Member;
import com.ditto.repository.BoardRepository;
import com.ditto.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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

    //게시글 상세보기
    public Optional<Board> getBoard(Long id){
        return boardRepository.findById(id);
    }

    //게시글 삭제
    public void deleteBoard(Long id){
        try {
            boardRepository.deleteById(id);
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            System.out.println("게시글이 없습니다.");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("게시글 삭제 중 예외 발생");
        }



    }

    //게시글 수정
    public void updateBoard(BoardDTO boardDTO){
        //게시글 상세보기 인 상태에서 수정하기 누르면 게시글 id 넘겨 받을 것
        //뷰에서 넘겨받은 content, title, boardCategory를 setDTO
        boardRepository.updateById(boardDTO.getTitle(),
                boardDTO.getContent(),
                boardDTO.getBoardCategory(),
                boardDTO.getId());
    }
}
