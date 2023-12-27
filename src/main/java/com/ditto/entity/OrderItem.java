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
@Table(name = "order_item")
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    //주문 가격과 아이템 이미지는 item 에서 가져올 수 있나?
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItem")
//    private List<ItemImg> itemImgList = new ArrayList<>();



    //구매일(regDate 로 되나?)


    //오더에 담기는 아이템은 여러개다. (기능이 하나라 서비스를 만들지 않았다.)
    public static OrderItem createOrderItem(Item item, int count, Order order){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrder(order);

        return orderItem;
    }



    //총가격
    public int totalPrice(){
        return item.getPrice() * count;
    }
    

}
