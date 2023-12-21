package com.ditto.service;


import com.ditto.dto.CartDetailDTO;
import com.ditto.dto.CartItemDTO;
import com.ditto.entity.Cart;
import com.ditto.entity.CartItem;
import com.ditto.entity.Item;
import com.ditto.entity.Member;
import com.ditto.repository.CartItemRepository;
import com.ditto.repository.CartRepository;
import com.ditto.repository.ItemRepository;
import com.ditto.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // 카트추가
    public Long addCart(CartItemDTO cartItemDTO, String id){
        Item item = itemRepository.findById(cartItemDTO.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByMemberId(id);
        Cart cart = cartRepository.findByMember_MemberId(member.getMemberId());
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if(savedCartItem != null){
            savedCartItem.addCount(cartItemDTO.getCount());
            return savedCartItem.getId();
        } else {
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDTO.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    // 장바구니 조회
    @Transactional(readOnly = true)
    public List<CartDetailDTO> getCartList(String id) {
        List<CartDetailDTO> cartDetailDTOList = new ArrayList<>();
        Member member = memberRepository.findByMemberId(id);
        Cart cart = cartRepository.findByMember_MemberId(member.getMemberId());
        if(cart == null){
            return cartDetailDTOList;
        }
        cartDetailDTOList = cartItemRepository.findCartDetailDTOList(cart.getId());
        return cartDetailDTOList;
    }

    @Transactional
    public boolean validateCartItem(Long cartItemId, String id){
        Member curMember = memberRepository.findByMemberId(id);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();
        if(!StringUtils.equals(curMember.getMemberId(), savedMember.getMemberId())){
            return false;
        }
        return true;
    }

    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItem.updateCount(count);
    }

    // 장바구니 삭제
    public void deleteCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    // 주문처리 후 장바구니 상품 제거

}
