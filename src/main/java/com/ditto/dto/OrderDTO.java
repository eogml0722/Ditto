package com.ditto.dto;

import com.ditto.constant.OrderStatus;
import com.ditto.entity.Member;
import com.ditto.entity.Order;
import com.ditto.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @ToString
public class OrderDTO {
    private Long id;
    private List<OrderItem> orderItemList;
    private LocalDateTime regTime;
    private OrderStatus orderStatus;

    private static ModelMapper modelMapper;

    private Order createOrder(){
        return modelMapper.map(this, Order.class);
    }

    

}
