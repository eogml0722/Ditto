package com.ditto.dto;

import com.ditto.constant.AskStatus;
import com.ditto.entity.AskBoard;
import com.ditto.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class BoardWriteDTO {
    private Long id;
    @NotBlank(message="제목은 필수 입력 값입니다.")
    private String title;
    @NotBlank(message="내용은 필수 입력 값입니다.")
    private String content;
    private AskStatus askStatus;
    private Member member;
    private List<AskBoardImageDTO> askBoardImageDTO = new ArrayList<>();
    private List<Long> askBoardImgIds = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    public AskBoard writeAskBoard(){
        return modelMapper.map(this, AskBoard.class);
    }

    public static BoardWriteDTO of(AskBoard askBoard){
        return modelMapper.map(askBoard, BoardWriteDTO.class);
    }

}
