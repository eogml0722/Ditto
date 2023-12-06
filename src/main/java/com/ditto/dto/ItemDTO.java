package com.ditto.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ItemDTO {
    private Long id;
    private String itemName;
    private String itemDetail;
    private int price;

    private String itemSellStatus; //물품 상태
    private String itemCategory; //물품 종류

    private LocalDateTime regTime; //등록 시간
    private LocalDateTime updateTime; //수정 시간

}
