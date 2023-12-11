package com.ditto.dto;

import com.ditto.constant.BoardCategory;
import com.ditto.entity.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @ToString
public class BoardFormDTO {

    //화면에 전달하기 위한 데이터
    private Long id;
    private String title;
    private String content;
    private String viewCount;
    private BoardCategory boardCategory;

    private List<Board> boardList;
    private LocalDateTime regDate;


}
