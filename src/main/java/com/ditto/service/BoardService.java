package com.ditto.service;


import com.ditto.constant.BoardCategory;
import com.ditto.dto.BoardDTO;
import com.ditto.entity.Board;
import com.ditto.entity.ItemImg;
import com.ditto.entity.Member;
import com.ditto.repository.BoardRepository;
import com.ditto.repository.ItemImgRepository;
import com.ditto.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ItemImgService itemImgService;


    //게시글 생성
    public Board insertBoard(Board board, List<MultipartFile> itemImgFileList) throws Exception {
        insertBoardDuplication(board);
        //이미지 등록
        for(int i=0; i<itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setBoard(board);
            if (i==0) {
                itemImg.setRepImgYn("Y"); //대표이미지 여부
            } else {
                itemImg.setRepImgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
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
        Member member = null;
        try {
            //글 작성자 정보
            member = memberRepository.findByMemberId(id);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("게시글 검색 중 예외 발생.");
        }
        //해당 회원이 작성한 글을 기준으로 검색
        return boardRepository.findByMember(member, title, content);
    }

    //게시글 상세보기
    public Board getBoard(Long id){
        Board board = null;
        try {
            board = boardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            board.plusViewCount();
        }catch (EntityNotFoundException e){
            System.out.println("해당하는 게시글이 없습니다.");
        }
        return board;
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
    public void updateBoard(BoardDTO boardDTO, List<MultipartFile> itemImgFileList) throws Exception {
        //게시글 상세보기 인 상태에서 수정하기 누르면 게시글 id 넘겨 받을 것
        //뷰에서 넘겨받은 content, title, boardCategory를 setDTO

        //Repasitory에 쿼리로 안 만들어줘도 사용할 수 있다.

        Board board = boardRepository.findById(boardDTO.getId()).orElseThrow(EntityNotFoundException::new);
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setBoardCategory(boardDTO.getBoardCategory());

        //상품이미지 파일 목록을 반복하며 이미지 등록을 위한 업데이트 메소드 호출
        for(int i=0; i<board.getImgList().size(); i++) {
            //상품 이미지 아이디, 새로운 이미지 파일 정보를 전달
            itemImgService.updateItemImg(board.getImgList().get(i).getId(), itemImgFileList.get(i));
        }
    }



    public Page<Board> findAllByOrderByIdDesc(Pageable pageable){
        return boardRepository.findAllByOrderByIdDesc(pageable);
    }

    public Page<Board> findByBoardCategory(Pageable pageable,BoardCategory boardCategory){
        return boardRepository.findByBoardCategory(pageable ,boardCategory);
    }


    public Page<Board> findByTitle(Pageable pageable, String keyword){
        return boardRepository.findByTitle(pageable, keyword);
    }


    public Page<Board> findByContent(Pageable pageable, String keyword){
        return boardRepository.findByContent(pageable, keyword);
    }

    public Page<Board> findBySearch(Pageable pageable, String keyword){
        return boardRepository.findBySearch(pageable, keyword);
    }

}
