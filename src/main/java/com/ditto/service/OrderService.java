package com.ditto.service;

import com.ditto.constant.OrderStatus;
import com.ditto.dto.OrderDTO;
import com.ditto.dto.OrderItemDTO;
import com.ditto.entity.Item;
import com.ditto.entity.Member;
import com.ditto.entity.Order;
import com.ditto.entity.OrderItem;
import com.ditto.repository.ItemRepository;
import com.ditto.repository.MemberRepository;
import com.ditto.repository.OrderItemRepository;
import com.ditto.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    //주문 조회
    public Page<OrderDTO> findByMemberId(String memberId, Pageable pageable) {
        if (memberId == null) {
            throw new NullPointerException("회원이 아닙니다.");
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();

        Page<Order> orderPage = orderRepository.findByMemberMemberIdOrderByRegTimeDesc(memberId, pageable);

        for (Order order : orderPage.getContent()) {

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setRegTime(order.getRegTime());
            orderDTO.setOrderStatus(order.getOrderStatus());
            orderDTO.setOrderItemList(order.getOrderItemList());
            orderDTOList.add(orderDTO);

//            orderDTOList.add(order.createOrderDTO());
        }
        return new PageImpl<>(orderDTOList, pageable, orderPage.getTotalElements());
    }


    //주문하기
    public Order saveOrder(Principal principal,OrderItemDTO orderItemDTO) throws Exception{
        //아이템아이디 , 수량, 사용자 정보
        Order order = new Order();
        order.setOrderStatus(OrderStatus.WAITDEPOSIT);

        //전달받은 아이템 정보로 오더아이템 생성
        /*
        Object itemIdObject = param.get("itemId");
        Object countObject = param.get("count");
        */
        Long itemId = orderItemDTO.getItemId();
        int count = orderItemDTO.getCount();


        Item item = itemRepository.findById(itemId).orElseThrow();
        item.changeStock(-1*count);


        //전달받은 아이템과 수량으로 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, count, order);
        //DB에 저장
        orderItemRepository.save(orderItem);


        //다대일 관계. 여러개의 상품이 담길 수 있게 리스트에 담기
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);


        //사용자 정보
        order.setMember(memberRepository.findByMemberId(principal.getName() ) );

        order.setOrderItemList(orderItemList);

        orderRepository.save(order);
        return order;
    }


    //주문취소
    public void cancelOrder(Principal principal, Long orderId) throws Exception{

        Order order = orderRepository.findById(orderId).orElseThrow();

        for (OrderItem orderItem : order.getOrderItemList()){
            orderItem.getItem().changeStock(orderItem.getCount());
        }

        if(StringUtils.equals(principal.getName(), order.getMember().getMemberId())) {
            order.cancelOrder();
        }


    }

    // 장바구니 주문


}
