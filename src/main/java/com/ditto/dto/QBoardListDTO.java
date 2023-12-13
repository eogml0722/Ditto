package com.ditto.dto;

import com.ditto.constant.ASKStatus;
import com.ditto.entity.AskBoard;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class QBoardListDTO {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String regTime;
    private ASKStatus qnaStatus;
    private List<BoardImageDTO> boardImageDTOList = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    public QBoardListDTO(AskBoard qboard){
        this.id = qboard.getId();
        this.title = qboard.getTitle();
        this.content = qboard.getContent();
        this.writer = qboard.getCreatedBy();
        this.regTime =qboard.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.qnaStatus = qboard.getQnaStatus();
    }

    public static QBoardListDTO of(AskBoard qboard){
        return modelMapper.map(qboard, QBoardListDTO.class);
    }


}
