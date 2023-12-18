package com.ditto.dto;

import com.ditto.constant.AskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskBoardSearchDTO {
    private String searchDateType;
    private AskStatus searchAskStatus;
    private String searchBy;
    private String searchQuery = "";
}
