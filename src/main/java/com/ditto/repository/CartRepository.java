package com.ditto.repository;

import com.ditto.entity.Cart;
import com.ditto.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByMember_MemberId(String memberId);
}