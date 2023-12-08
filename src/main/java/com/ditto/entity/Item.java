package com.ditto.entity;

import com.ditto.constant.ItemCategory;
import com.ditto.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.security.Principal;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter @Setter @ToString
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String itemName;

    //@Lob 은 Large Object. 글자를 많이 써야할 때 (데이터가 많을 때 사용)
    @Lob
    private String itemDetail;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;

    //아이템 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    //아이템 분류
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;

    
    //나중에 Auditing 할것
    private LocalDateTime regTime; //등록 시간
    private LocalDateTime updateTime; //수정 시간




}
