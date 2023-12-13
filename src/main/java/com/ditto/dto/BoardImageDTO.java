package com.ditto.dto;

import com.ditto.entity.BoardImage;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Setter
@Getter
public class BoardImageDTO {
    private Long id;
    private String oname;
    private String sname;
    private String url;
    private static ModelMapper modelMapper = new ModelMapper();

    public static BoardImageDTO of(BoardImage boardImage){
        return modelMapper.map(boardImage, BoardImageDTO.class);
    }

}
