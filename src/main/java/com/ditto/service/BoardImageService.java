package com.ditto.service;

import com.ditto.entity.BoardImage;
import com.ditto.repository.BoardImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardImageService {

    @Value("${boardImgLocation}")
    private String boardImgLocation;
    private final BoardImageRepository boardImageRepository;
    private final FileService fileService;

    public void saveBoardImage(BoardImage boardImage, MultipartFile boardImgFile) throws Exception{
        String oname = boardImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oname)){
            imgName = fileService.uploadFIle(boardImgLocation, oname, boardImgFile.getBytes());
            imgUrl = "/images/board/" + imgName;
        }

        boardImage.updateBoardImg(oname, imgName, imgUrl);
        boardImageRepository.save(boardImage);

    }




}
