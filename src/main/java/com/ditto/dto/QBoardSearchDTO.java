package com.ditto.dto;

import com.ditto.constant.ASKStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QBoardSearchDTO {
    private String searchDateType;
    private ASKStatus searchQNAStatus;
    private String searchBy;
    private String searchQuery = "";
}
