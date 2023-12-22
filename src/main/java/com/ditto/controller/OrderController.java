package com.ditto.controller;

import com.ditto.dto.OrderDTO;
import com.ditto.entity.Order;
import com.ditto.entity.OrderItem;
import com.ditto.repository.OrderRepository;
import com.ditto.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.util.StringUtils;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
private final OrderService orderService;


    @GetMapping(value = "/order")
    public String goOrder(Principal principal, Model model, Optional<Integer> page){
        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<OrderDTO> orderDTOPage = orderService.findByMemberId(principal.getName(), pageable);


        model.addAttribute("orderDTOPage", orderDTOPage);
        model.addAttribute("maxPage",10);

        return "order/orderCheck";
    }

}
