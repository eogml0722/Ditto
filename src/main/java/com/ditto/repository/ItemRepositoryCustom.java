package com.ditto.repository;

import  com.ditto.entity.Item;
import com.ditto.dto.ItemSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);
}
