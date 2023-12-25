package com.ditto.controller;

import com.ditto.dto.OrderDTO;
import com.ditto.dto.OrderItemDTO;
import com.ditto.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @GetMapping(value = "/order")
    public String goOrder(Principal principal, Model model, Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<OrderDTO> orderDTOPage = orderService.findByMemberId(principal.getName(), pageable);
        model.addAttribute("orderDTOPage", orderDTOPage);
        model.addAttribute("maxPage", 10);

        return "order/orderCheck";
    }


    @ResponseBody
    @PostMapping(value = "/order")
    public String getOrder(Principal principal, Model model, Optional<Integer> page,
                                   @RequestBody OrderItemDTO orderItemDTO) {

        System.out.println(orderItemDTO.getCount());
        System.out.println(orderItemDTO.getItemId());

        orderService.saveOrder(principal, orderItemDTO);


        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<OrderDTO> orderDTOPage = orderService.findByMemberId(principal.getName(), pageable);
        model.addAttribute("orderDTOPage", orderDTOPage);
        model.addAttribute("maxPage", 10);

        return "/order/orderCheck";

        /*
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String id = principal.getName();

        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<OrderDTO> orderDTOPage = orderService.findByMemberId(principal.getName(), pageable);
        model.addAttribute("orderDTOPage", orderDTOPage);
        model.addAttribute("maxPage", 10);

        try {
            //json 으로 넘겨 받은 item id와 count로 주문등록.
            orderService.saveOrder(principal, orderItemDTO);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(HttpStatus.OK);
*/
    }
}