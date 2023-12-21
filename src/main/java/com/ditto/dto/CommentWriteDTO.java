package com.ditto.dto;

import com.ditto.entity.AskBoard;
import com.ditto.entity.AskComment;
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
public class CommentWriteDTO {
    private Long id;
    @NotBlank(message="내용은 필수 입력 값입니다.")
    private String content;
    private Member member;
    private List<AskBoardImageDTO> askBoardImageDTO = new ArrayList<>();
    private List<Long> askBoardImgIds = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    public AskComment writeAskComment(){
        return modelMapper.map(this, AskComment.class);
    }

    public static CommentWriteDTO of(AskComment askComment){
        return modelMapper.map(askComment, CommentWriteDTO.class);
    }


}
