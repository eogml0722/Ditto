package com.ditto.service;

import com.ditto.entity.Img;
import com.ditto.entity.ItemImg;
import com.ditto.repository.ImgRepository;
import com.ditto.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;


@Service
@RequiredArgsConstructor
@Transactional
public class ImgService {

    @Value("${itemImgLocation}")
    private String itemImgLocation;
    private final ImgRepository imgRepository;
    private final FileService fileService;
    public void saveImg(Img img, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //상품이미지를 등록했다면
        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            //저장된 상품 이미지를 불러올 경로를 설정
            imgUrl = "/images/item/"+imgName;
        }

        //상품 이미지 정보를 저장
        img.updateImg(oriImgName, imgName, imgUrl);
        imgRepository.save(img);
    }

    //상품 이미지 변경하는 메서드
    //기존 이미지의 id와 새로 업로드하는 이미지 파일을 매개변수로 전달
    public void updateItemImg(Long id, MultipartFile itemImgFile) throws Exception{
        //새로 업로드된 이미지가 공백이 아니면
        if(!itemImgFile.isEmpty()) {
            //기존 이미지 id를 이용하여 저장된 데이터가 있는지 확인
            Img savedImg = imgRepository.findByIdOrderByIdAsc(id)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }

            //새로운 이미지 업로드
            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }
}
