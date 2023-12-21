package com.ditto.service;

import com.ditto.constant.ItemCategory;
import com.ditto.constant.ItemSellStatus;
import com.ditto.dto.ItemFormDTO;
import com.ditto.entity.Item;
import com.ditto.entity.ItemImg;
import com.ditto.repository.ItemImgRepository;
import com.ditto.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemServiceTest {
    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    //이미지 파일 목록을 생성
    List<MultipartFile> createMultipartFiles() throws Exception {
        List<MultipartFile> multipartFileList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String path = "D:/ditto/item/";
            String imageName = "image" + i + ".jpg";

            //가상의 이미지를 생성하고 리스트에 추가
            MockMultipartFile multipartFile = new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1, 2, 3, 4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }
    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")  //가상의 관리자 설정
    void saveItem() throws Exception {
        //가상의 데이터
        ItemFormDTO itemFormDTO = new ItemFormDTO();
        itemFormDTO.setItemName("테스트상품");
        itemFormDTO.setItemSellStatus(ItemSellStatus.SELL);
        itemFormDTO.setItemCategory(ItemCategory.DESSERT);
        itemFormDTO.setItemDetail("테스트 상품입니다.");
        itemFormDTO.setPrice(1000);
        itemFormDTO.setStockNumber(100);
        //가짜 이미지 목록을 생성
        List<MultipartFile> multipartFileList = createMultipartFiles();

        //상품을 등록하고 결과로 상품id를 저장
        Long itemId = itemService.saveItem(itemFormDTO, multipartFileList);

        //상품 정보 및 이미지를 조회
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        //저장되어있는 내용과 저장한 내용이 동일지 확인
        assertEquals(itemFormDTO.getItemName(), item.getItemName());
        assertEquals(itemFormDTO.getItemSellStatus(), item.getItemSellStatus());
        assertEquals(itemFormDTO.getItemCategory(), item.getItemCategory());
        assertEquals(itemFormDTO.getItemDetail(), item.getItemDetail());
        assertEquals(itemFormDTO.getPrice(), item.getPrice());
        assertEquals(itemFormDTO.getStockNumber(), item.getStockNumber());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());
    }
}

