package com.ditto.controller;

import com.ditto.dto.ItemFormDTO;
import com.ditto.service.ImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImgController {

    //상품 등록을 처리하는 메서드
    //@Valid ItemFormDTO itemFormDto : 유효성 검사를 수행
    //BindingResult bindingResult : 유효성검사의 결과를 저장
    //Model model : 뷰에 전달할 데이터를 저장
    //@RequestParam("itemImgFile")List<MultipartFile> itemImgFileList : 상품 이미지 파일 목록
    @
}
