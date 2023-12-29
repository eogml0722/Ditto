package com.ditto.entity;


import com.ditto.constant.BoardCategory;
import com.ditto.dto.BoardDTO;
import com.ditto.dto.BoardFormDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.web.accept.MappingMediaTypeFileExtensionResolver;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter @Setter @ToString
public class Board extends BaseEntity {
    @Id
    @Column(name = "board_id")
    @GeneratedValue
    private Long id;

    //멤버는 BaseEntity로 대체

    @Column(nullable = false)
    private String title;

    private String content;

    //조회수
    private int viewCount;

    //게시판 분류
    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    //사용자 정보
    private Member member;

    
    //이미지 업로드
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImg> imgList = new ArrayList<>();


    public static Board createBoard(BoardDTO boardDTO, Member member) {
        //멤버와 보드 연결 (modelMapper 사용)
        Board board = boardDTO.createBoard();
        board.setMember(member);

        return board;
    }


    //조회수 증가
    public void plusViewCount(){
        this.viewCount ++;
    }

}
