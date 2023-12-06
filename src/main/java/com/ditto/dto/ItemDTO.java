package com.ditto.dto;

import com.ditto.constant.ItemCategory;
import com.ditto.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ItemDTO {
    private Long id;
    private String itemName;
    private String itemDetail;
    private int price;

    private ItemSellStatus itemSellStatus; //물품 상태
    private ItemCategory itemCategory; //물품 종류

    private LocalDateTime regTime; //등록 시간
    private LocalDateTime updateTime; //수정 시간

}
