package com.ditto.repository;

import com.ditto.entity.Order;
import com.ditto.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    Page<Order> findByMemberMemberId(String memberId, Pageable pageable);
}
