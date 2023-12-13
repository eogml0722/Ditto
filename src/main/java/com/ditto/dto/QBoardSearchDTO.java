package com.ditto.dto;

import com.ditto.constant.QNAStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QBoardSearchDTO {
    private String searchDateType;
    private QNAStatus searchQNAStatus;
    private String searchBy;
    private String searchQuery = "";
}
