package com.ditto.dto;

import com.ditto.entity.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;

@Getter
@Setter
@ToString
public class CartDetailDTO {
    private Long cartItemId;
    private String itemName;
    private int price;
    private int count;
    private String imgUrl;

    public CartDetailDTO(Long cartItemId, String itemName, int price, int count, String imgUrl) {
        this.cartItemId = cartItemId;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
