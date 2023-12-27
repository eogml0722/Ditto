package com.ditto.dto;

import com.ditto.constant.BoardCategory;
import com.ditto.entity.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter @Setter @ToString
public class BoardFormDTO {

    //화면에 전달하기 위한 데이터
    private Long id;
    private String title;
    private String content;
    private String viewCount;
    private BoardCategory boardCategory;

    //Auditing
    private List<Board> boardList;
    private LocalDateTime regTime;

    //BoardFormDTO 로 분리

    //검색필드
    private String searchField;
    private String searchOption;


    //이전행
    private Optional<Board> boardPrev;
    //다음행
    private Optional<Board> boardNext;


    private static ModelMapper modelMapper = new ModelMapper();

    public Board createBoard(){
        return  modelMapper.map(this, Board.class );
    }

    public static BoardFormDTO of(Board board){
        return modelMapper.map(board, BoardFormDTO.class);
    }
}
