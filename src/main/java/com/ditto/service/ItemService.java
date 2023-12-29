package com.ditto.service;

import com.ditto.constant.ItemCategory;
import com.ditto.dto.*;
import com.ditto.entity.Board;
import com.ditto.entity.Item;
import com.ditto.entity.ItemImg;
import com.ditto.repository.BoardRepository;
import com.ditto.repository.ItemImgRepository;
import com.ditto.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception {

        //상품등록
        Item item = itemFormDTO.createItem();
        itemRepository.save(item);

        //이미지 등록
        for(int i=0; i<itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if (i==0) {
                itemImg.setRepImgYn("Y"); //대표이미지 여부
            } else {
                itemImg.setRepImgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();
    }

    //상품 수정
    @Transactional(readOnly = true) //읽기전용
    public ItemFormDTO getItemDtl(Long itemId) {
        //상품 이미지 목록 조회
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        //상품 이미지를 조회하여
        //itemImgDTO로 리스트에 추가함
        List<ItemImgDTO> itemImgDTOList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDTO itemImgDTO = ItemImgDTO.of(itemImg);
            itemImgDTOList.add(itemImgDTO);
        }

        //상품id로 상품을 조회하고 존재하지 않으면 예외 발생
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        //ItemFormDTO에 상품 정보를 설정하고 목록에 추가후 리턴
        ItemFormDTO itemFormDTO = ItemFormDTO.of(item);
        itemFormDTO.setItemImgDTOList(itemImgDTOList);
        return itemFormDTO;
    }

    //상품정보, 이미지 파일 목록을 매개변수로 받아옴
    public Long updateItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception {
        //상품id에 해당하는 상품을 조회
        Item item = itemRepository.findById(itemFormDTO.getId())
                .orElseThrow(EntityNotFoundException::new);

        //상품 정보를 변경(이름,가격,재고수량...)
        item.updateItem(itemFormDTO);

        //이미지 목록을 조회
        List<Long> itemImgIds = itemFormDTO.getItemImgIds();

        //상품이미지 파일 목록을 반복하며 이미지 등록을 위한 업데이트 메소드 호출
        for(int i=0; i<itemImgFileList.size(); i++) {
            //상품 이미지 아이디, 새로운 이미지 파일 정보를 전달
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        return item.getId();
    }

    //상품조회 조건, 페이지 정보를 읽어서 파라미터로 조회
    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDTO, pageable);
    }

    //메인 페이지에 보여줄 상품 데이터 조회
    @Transactional(readOnly = true)
    public Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDTO, pageable);
    }

    // 카테고리별 아이템 분류
    @Transactional(readOnly = true)
    public ItemCategory getCategory(String category){
        ItemCategory itemCategory = null;
        if(category.equals("COFFEE")){
            itemCategory = ItemCategory.COFFEE;
        } else if (category.equals("BEVERAGE")) {
            itemCategory = ItemCategory.BEVERAGE;
        } else if (category.equals("DESSERT")) {
            itemCategory = ItemCategory.DESSERT;
        } else if (category.equals("FRAPPE")) {
            itemCategory = ItemCategory.FRAPPE;
        }
        return itemCategory;
    }

}
