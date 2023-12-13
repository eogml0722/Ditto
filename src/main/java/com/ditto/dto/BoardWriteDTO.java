package com.ditto.dto;

import com.ditto.constant.ASKStatus;
import com.ditto.entity.AskBoard;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardWriteDTO {
    private Long id;
    @NotBlank(message="제목은 필수 입력 값입니다.")
    private String title;
    @NotBlank(message="내용은 필수 입력 값입니다.")
    private String content;
    private ASKStatus qnaStatus;
    private List<BoardImageDTO> boardImageDTO = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    public AskBoard createQBoard(){
        return modelMapper.map(this, AskBoard.class);
    }

    public static BoardWriteDTO of(AskBoard qboard){
        return modelMapper.map(qboard, BoardWriteDTO.class);
    }

}
