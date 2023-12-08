package com.ditto.service;

import com.ditto.constant.BoardCategory;
import com.ditto.dto.MemberFormDTO;
import com.ditto.entity.Board;
import com.ditto.entity.Member;
import com.ditto.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
//테스트 서버로 테스트 실행
@TestPropertySource(locations = "/application-test.properties")
class BoardServiceTest {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;


    //맴버 생성
    @Test
    @DisplayName("멤버 생성")
    public void createMemberTest(){
        /*
        MemberFormDTO memberFormDTO = new MemberFormDTO();
        memberFormDTO.set();
        memberFormDTO.set();
        memberFormDTO.set();
        memberFormDTO.set();
        memberFormDTO.set();
        */

    }


    //게시판 생성 테스트
    @Test
    @DisplayName("게시판 생성 테스트")
    public void createBoardTest(){


    }


}