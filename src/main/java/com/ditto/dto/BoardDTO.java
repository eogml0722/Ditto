package com.ditto.dto;

import com.ditto.constant.BoardCategory;
import com.ditto.entity.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;

@Getter @Setter @ToString
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private String viewCount;
    private BoardCategory boardCategory;

    private static ModelMapper modelMapper = new ModelMapper();

    public Board createBoard(){
        return  modelMapper.map(this, Board.class );
    }

    public static BoardDTO of(Board board){
        return modelMapper.map(board, BoardDTO.class);
    }
}
