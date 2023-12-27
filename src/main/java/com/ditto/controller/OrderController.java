package com.ditto.controller;

import com.ditto.dto.OrderDTO;
import com.ditto.dto.OrderItemDTO;
import com.ditto.entity.Order;
import com.ditto.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;



    @GetMapping(value = {"/order", "/order/{page}"})
    public String goOrder(Principal principal, Model model,@PathVariable("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.map(integer -> integer - 1).orElse(0), 5);
        Page<OrderDTO> orderDTOPage = orderService.findByMemberId(principal.getName(), pageable);

        model.addAttribute("orderDTOPage", orderDTOPage);
        model.addAttribute("maxPage", 10);



        return "order/orderCheck";
    }


    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity getOrder(Principal principal, Model model, Optional<Integer> page,
                                   @RequestBody @Valid OrderItemDTO orderItemDTO,
                           BindingResult bindingResult) {

        //유효성 검사 및 내용
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        

        try {
            //json 으로 넘겨 받은 item id와 count로 주문등록.
            Order order = orderService.saveOrder(principal, orderItemDTO);
//            OrderDTO orderDTO = order.createOrderDTO();
//            model.addAttribute(orderDTO);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<OrderDTO> orderDTOPage = orderService.findByMemberId(principal.getName(), pageable);
        model.addAttribute("orderDTOPage", orderDTOPage);
        model.addAttribute("maxPage", 10);
        //주문 목록을 위해 orderDTO


        return new ResponseEntity(HttpStatus.OK);

    }

    @PostMapping(value = "/order/{orderId}")
    public @ResponseBody ResponseEntity cancelOrder(Principal principal, @PathVariable("orderId") Long orderId) {
        //주문번호가 오면 그 주문번호 삭제.
        try {
            orderService.cancelOrder(principal, orderId);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }






}