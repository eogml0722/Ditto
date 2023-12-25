package com.ditto.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter @ToString
public class OrderItemDTO {

    //주문한 OrderItemDTO
    @NotNull
    private Long itemId;
    @Min(value = 1)
    private int count;
}
