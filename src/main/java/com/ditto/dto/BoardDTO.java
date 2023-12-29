package com.ditto.dto;

import com.ditto.constant.BoardCategory;
import com.ditto.entity.Board;
import com.ditto.entity.ItemImg;
import com.ditto.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter @Setter @ToString
public class BoardDTO {
    private Long id;
    @NotBlank(message = "제목을 입력하세요.")
    private String title;
    private String content;
    private String viewCount;
    private BoardCategory boardCategory;

    private List<ItemImg> imgList;


    //Auditing
    private List<Board> boardList;
    private LocalDateTime regTime;

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

    public static BoardDTO of(Board board){
        return modelMapper.map(board, BoardDTO.class);
    }
}
