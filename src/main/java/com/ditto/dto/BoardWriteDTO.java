package com.ditto.dto;

import com.ditto.constant.QNAStatus;
import com.ditto.entity.aQBoard;
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
    private QNAStatus qnaStatus;
    private List<BoardImageDTO> boardImageDTO = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    public aQBoard createQBoard(){
        return modelMapper.map(this, aQBoard.class);
    }

    public static BoardWriteDTO of(aQBoard qboard){
        return modelMapper.map(qboard, BoardWriteDTO.class);
    }

}
