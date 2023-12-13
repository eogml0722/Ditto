package com.ditto.dto;

import com.ditto.constant.QNAStatus;
import com.ditto.entity.aQBoard;
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
    private QNAStatus qnaStatus;
    private List<BoardImageDTO> boardImageDTOList = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    public QBoardListDTO(aQBoard qboard){
        this.id = qboard.getId();
        this.title = qboard.getTitle();
        this.content = qboard.getContent();
        this.writer = qboard.getCreatedBy();
        this.regTime =qboard.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.qnaStatus = qboard.getQnaStatus();
    }

    public static QBoardListDTO of(aQBoard qboard){
        return modelMapper.map(qboard, QBoardListDTO.class);
    }


}
