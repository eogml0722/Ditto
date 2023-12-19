package com.ditto.entity;

import com.ditto.constant.ItemCategory;
import com.ditto.constant.ItemSellStatus;
import com.ditto.dto.ItemFormDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.security.Principal;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter @Setter @ToString
public class Item extends BaseEntity{
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //상품코드

    @Column(nullable = false, length = 100)
    private String itemName; //상품명

    //@Lob 은 Large Object. 글자를 많이 써야할 때 (데이터가 많을 때 사용)
    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    @Column(name="price", nullable = false)
    private int price; //가격

    @Column(nullable = false)
    private int stockNumber; //재고수량

    //아이템 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    //아이템 분류
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;

    public void updateItem(ItemFormDTO itemFormDTO) {
        this.itemName = itemFormDTO.getItemName();
        this.price = itemFormDTO.getPrice();
        this.stockNumber = itemFormDTO.getStockNumber();
        this.itemDetail = itemFormDTO.getItemDetail();
        this.itemSellStatus = itemFormDTO.getItemSellStatus();
    }


}
