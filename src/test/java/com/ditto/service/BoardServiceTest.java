package com.ditto.service;

import com.ditto.constant.BoardCategory;
import com.ditto.constant.OAuthType;
import com.ditto.constant.Role;
import com.ditto.dto.BoardDTO;
import com.ditto.dto.BoardFormDTO;
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

import javax.persistence.Column;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
@TestPropertySource(locations ="classpath:application.properties")
class BoardServiceTest {
    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

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
        memberFormDTO.setMemberId("asd1");
        memberFormDTO.setPassword("asd1");
        memberFormDTO.setName("aaa1");
        memberFormDTO.setPhoneNum("1234");
        memberFormDTO.setZipcode("1234");
        memberFormDTO.setStreetAddress("aaa");
        memberFormDTO.setDetailAddress("aaa");
        memberFormDTO.setEmail("aaaa");


        Member member = Member.createMember(memberFormDTO, passwordEncoder);
        memberService.saveMember(member);
        member = memberService.saveMember(member);

        return member;
    }



    //게시판 생성 테스트

    @Test
    @Rollback(false)
    @DisplayName("게시판 생성 테스트")
    public void createBoardTest(){
//        Member member = createMemberTest();
        for(int i=1 ; i<200 ; i++){
            Member member = memberRepository.findByMemberId("asdasd1");
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setTitle("제목" + i);
            boardDTO.setContent("내용" + i);
            boardDTO.setBoardCategory(BoardCategory.NEWS);
            Board board = Board.createBoard(boardDTO, member);
//            boardService.insertBoard(board);
        }

        /*

        Board result = boardRepository.saveAndFlush(board);
        Board findBoard =boardRepository.findById(result.getId())
                .orElseThrow(EntityNotFoundException::new);
        System.out.println(findBoard);
        */
    }


    //전체 게시글 조회
    @Test
    @DisplayName("전체 게시글 조회")
    public void getAllBoardList(){
       List<Board> boardList =  boardRepository.findAll();
       for (Board board : boardList){
           System.out.println(board);
       }
    }


    //게시글 검색
    //아이디 및 카테고리만 
    //검색어 조회는 추가할 것
    @Test
    @DisplayName("게시글 검색")
    public void selectBoardTest(){
       List<Board> boardList = boardService.selectBoard("asd", null, "내용");

        for (Board board : boardList){
            System.out.println(board);
        }

    }
    
    //게시글 제목 클릭시 내용보기
    @Test
    @DisplayName("게시글 상세 보기")
    public void getBoardTest(){
        //클릭하면 get으로 컨트롤러에서 게시글 id 받아온다.
        //게시글 id로 내용 받아오기
        System.out.println(boardService.getBoard(1L) );
    }

    //게시글 수정
    @Test
    @DisplayName("게시글 수정")
    public void updateBoardTest(){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle("test11");
        boardDTO.setContent("내용11");
        boardDTO.setBoardCategory(BoardCategory.ANSWER);
        boardDTO.setId(1L);
//        boardService.updateBoard(boardDTO);
        System.out.println(boardService.getBoard(1L) );
    }

    @Test
    @Rollback(value = false)
    @DisplayName("게시글 삭제")
    public void deleteBoardTest(){
        boardService.deleteBoard(3L);
        System.out.println(boardService.getBoard(3L) );
    }

}