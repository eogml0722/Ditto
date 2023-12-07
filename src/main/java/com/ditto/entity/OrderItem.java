package com.ditto.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
public class OrderItem {
    //주문할 아이템
    @Id
    @Column(name = "orderItem_id")
    @GeneratedValue
    private Long id;

    //갯수
    private int count;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order")
    private Order order;

    //주문 가격과 아이템 이미지는 item 에서 가져올 수 있나?

}
