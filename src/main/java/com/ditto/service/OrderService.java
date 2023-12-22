package com.ditto.service;

import com.ditto.constant.OrderStatus;
import com.ditto.dto.OrderDTO;
import com.ditto.entity.Order;
import com.ditto.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Page<OrderDTO> findByMemberId(String memberId, Pageable pageable) {
        if (memberId == null) {
            throw new NullPointerException("회원이 아닙니다.");
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();

        Page<Order> orderPage = orderRepository.findByMemberMemberId(memberId, pageable);

        for (Order order : orderPage.getContent()) {
            /*
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setRegTime(order.getRegTime());
            orderDTO.setOrderStatus(order.getOrderStatus());
            orderDTO.setOrderItemList(order.getOrderItemList());
            orderDTOList.add(orderDTO);
             */
            orderDTOList.add(order.createOrderDTO());
        }
        return new PageImpl<>(orderDTOList, pageable, orderPage.getTotalElements());
    }
}
