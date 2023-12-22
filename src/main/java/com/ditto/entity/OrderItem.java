package com.ditto.entity;

import com.ditto.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @ToString
public class OrderItem extends BaseEntity{
    //주문할 아이템
    @Id
    @Column(name = "orderItem_id")
    @GeneratedValue
    private Long id;

    //주문 수량
    private int count;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    //주문 가격과 아이템 이미지는 item 에서 가져올 수 있나?
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItem")
    private List<ItemImg> itemImgList = new ArrayList<>();


    //구매일(regDate 로 되나?)



    //총가격
    public int totalPrice(){
        return item.getPrice() * count;
    }
    

}
