package com.ditto.dto;

import com.ditto.constant.AskStatus;
import com.ditto.entity.AskBoard;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class AskBoardListDTO {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String regTime;
    private AskStatus askStatus;
    private List<AskBoardImageDTO> askBoardImageDTOList = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    public AskBoardListDTO(AskBoard askBoard){
        this.id = askBoard.getId();
        this.title = askBoard.getTitle();
        this.content = askBoard.getContent();
        this.writer = askBoard.getCreatedBy();
        this.regTime =askBoard.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.askStatus = askBoard.getAskStatus();
    }

    public static AskBoardListDTO of(AskBoard askboard){
        return modelMapper.map(askboard, AskBoardListDTO.class);
    }


}
