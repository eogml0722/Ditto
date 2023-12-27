package com.ditto.entity;

import com.ditto.constant.OrderStatus;
import com.ditto.dto.OrderDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter @ToString
public class Order extends BaseEntity{
    //주문
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany (mappedBy = "order" , fetch = FetchType.LAZY,
                cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();

    //구매상태. (입금대기, 주문완료, 배송준비중, 배송중, 배송완료(자동구매 카운트 시작), 구매확정)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    //지연로딩
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public int totalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItemList){
            totalPrice += orderItem.totalPrice();
        }
        return totalPrice;
    }

    private static ModelMapper modelMapper;
    //매핑
    public OrderDTO createOrderDTO(){
        return modelMapper.map(this, OrderDTO.class);
    }


    //주문취소 메서드(주문상태 변경)
    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;
    }
}
