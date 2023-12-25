package com.ditto.service;

import com.ditto.dto.OrderItemDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@TestPropertySource(locations ="classpath:application.properties")
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("주문 테스트")
    public void orderTest(){
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setCount(1);
        orderItemDTO.setItemId(1L);

//        orderService.saveOrder(memberService.findId("asd","asd@asd.asd"), orderItemDTO);

    }


}