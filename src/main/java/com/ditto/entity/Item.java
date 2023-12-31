package com.ditto.entity;

import com.ditto.constant.ItemCategory;
import com.ditto.constant.ItemSellStatus;
import com.ditto.dto.ItemFormDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Min(0)
    private int stockNumber; //재고수량

    //아이템 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    //아이템 분류
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<ItemImg> itemImgList = new ArrayList<>();

    public void updateItem(ItemFormDTO itemFormDTO) {
        this.itemName = itemFormDTO.getItemName();
        this.price = itemFormDTO.getPrice();
        this.stockNumber = itemFormDTO.getStockNumber();
        this.itemDetail = itemFormDTO.getItemDetail();
        this.itemSellStatus = itemFormDTO.getItemSellStatus();
        this.itemCategory = itemFormDTO.getItemCategory();
    }

    //주문시 재고 변경
    public void changeStock(int count) throws Exception{
        if(stockNumber + count < 0) {
            throw new Exception("재고가 부족합니다.");
        }
        this.stockNumber += count;
    }
}
