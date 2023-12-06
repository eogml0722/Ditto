package com.ditto.service;

import com.ditto.dto.ItemDTO;
import com.ditto.entity.Item;
import com.ditto.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;




}
