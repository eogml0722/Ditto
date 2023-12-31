package com.ditto.repository;

import com.ditto.dto.CartDetailDTO;
import com.ditto.entity.CartItem;
import com.ditto.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select new com.ditto.dto.CartDetailDTO(ci.id, i.itemName, i.price, ci.count, im.imgUrl) "
            +"from CartItem ci, ItemImg im "
            +"join ci.item i "
            +"where ci.cart.id = :cartId "
            +"and im.item.id = ci.item.id "
            +"and im.repImgYn = 'Y' "
            +"order by ci.regTime desc")
    List<CartDetailDTO> findCartDetailDTOList(Long cartId);
}
