package com.ditto.service;

import com.ditto.entity.AskBoardImage;
import com.ditto.repository.AskBoardRepository;
import com.ditto.repository.BoardImageRepository;
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
public class BoardImageService {

    @Value("${boardImgLocation}")

    private String boardImgLocation;
    private final BoardImageRepository boardImageRepository;
    private final FileService fileService;

    public void saveBoardImage(AskBoardImage askBoardImage, MultipartFile boardImgFile) throws Exception{
        String oname = boardImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oname)){
            imgName = fileService.uploadFIle(boardImgLocation, oname, boardImgFile.getBytes());
            imgUrl = "/images/board/" + imgName;
        }

        askBoardImage.updateBoardImg(oname, imgName, imgUrl);
        boardImageRepository.save(askBoardImage);

    }

    public void updateBoardImage(Long askBoardImgId, MultipartFile askBoardImgFile) throws Exception{
        if(!askBoardImgFile.isEmpty()){
            AskBoardImage savedAskBoardImg = boardImageRepository.findById(askBoardImgId).orElseThrow(EntityNotFoundException::new);

            if(!StringUtils.isEmpty(savedAskBoardImg.getSname())){
                fileService.deleteFile(boardImgLocation+"/"+savedAskBoardImg.getSname());
            }

            String oname = askBoardImgFile.getOriginalFilename();
            String sname = fileService.uploadFIle(boardImgLocation, oname, askBoardImgFile.getBytes());
            String url = "/images/board/" + sname;

            savedAskBoardImg.updateBoardImg(oname, sname, url);

        }
    }


}
