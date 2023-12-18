package com.ditto.dto;

import com.ditto.entity.AskBoardImage;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Setter
@Getter
public class AskBoardImageDTO {
    private Long id;
    private String oname;
    private String sname;
    private String url;
    private static ModelMapper modelMapper = new ModelMapper();

    public static AskBoardImageDTO of(AskBoardImage askBoardImage){
        return modelMapper.map(askBoardImage, AskBoardImageDTO.class);
    }

}
