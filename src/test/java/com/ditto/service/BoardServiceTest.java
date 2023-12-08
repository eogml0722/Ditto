package com.ditto.service;

import com.ditto.constant.BoardCategory;
import com.ditto.dto.BoardDTO;
import com.ditto.dto.MemberFormDTO;
import com.ditto.entity.Board;
import com.ditto.entity.Member;
import com.ditto.repository.BoardRepository;
import com.ditto.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
//테스트 서버로 테스트 실행
@TestPropertySource(locations ="classpath:application-test.properties")
class BoardServiceTest {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    //맴버 생성

    @Test
    @DisplayName("멤버 생성")
    public Member createMemberTest(){

        MemberFormDTO memberFormDTO = new MemberFormDTO();
        memberFormDTO.setMemberId("asd");
        memberFormDTO.setPassword("asd");
        memberFormDTO.setName("sad");
        memberFormDTO.setPhoneNum1(45);
        memberFormDTO.setPhoneNum2(12);
        memberFormDTO.setAddress("asd");
        memberFormDTO.setEmail("sad");

        Member member = Member.createMember(memberFormDTO, passwordEncoder);

        member = memberRepository.save(member);

        return member;
    }



    //게시판 생성 테스트
    @Test
//    @Rollback(false)
    @DisplayName("게시판 생성 테스트")
    public void createBoardTest(){
        Member member = createMemberTest();
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle("test1");
        boardDTO.setContent("내용1");
        boardDTO.setBoardCategory(BoardCategory.EVENT);

        Board board = Board.createBoard(boardDTO, member);
        Board result = boardRepository.saveAndFlush(board);
        Board findBoard =boardRepository.findById(result.getId())
                .orElseThrow(EntityNotFoundException::new);

        System.out.println(findBoard);

    }


}