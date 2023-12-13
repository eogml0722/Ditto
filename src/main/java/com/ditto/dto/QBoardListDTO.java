package com.ditto.dto;

import com.ditto.constant.QNAStatus;
import com.ditto.entity.aQBoard;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class QBoardListDTO {
    private Long id;
    private String title;
    private String writer;
    private String regTime;
    private QNAStatus qnaStatus;

    public QBoardListDTO(aQBoard qboard){
        this.id = qboard.getId();
        this.title = qboard.getTitle();
        this.writer = qboard.getCreatedBy();
        this.regTime =qboard.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.qnaStatus = qboard.getQnaStatus();
    }
}
