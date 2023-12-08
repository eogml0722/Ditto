/*
package com.ditto.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter @ToString
public class Order {
    //주문
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany (mappedBy = "order",cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();
}
*/
