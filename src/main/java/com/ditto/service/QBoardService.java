package com.ditto.service;


import com.ditto.constant.QNAStatus;
import com.ditto.dto.BoardWriteDTO;
import com.ditto.dto.QBoardSearchDTO;
import com.ditto.entity.BoardImage;
import com.ditto.entity.aQBoard;
import com.ditto.repository.BoardImageRepository;
import com.ditto.repository.QBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QBoardService {

    private final QBoardRepository qboardRepository;
    private final BoardImageService boardImageService;
    private final BoardImageRepository boardImageRepository;

    public Long writeBoard(BoardWriteDTO boardWriteDTO, List<MultipartFile> boardImgFileList) throws Exception{
        aQBoard aQBoard = new aQBoard();
        aQBoard = boardWriteDTO.createQBoard();
        aQBoard.setQnaStatus(QNAStatus.NOT_ANSWER);
        qboardRepository.save(aQBoard);
        System.out.println("qboardservice :" + boardImgFileList );
        for(int i=0;i<boardImgFileList.size();i++){
            BoardImage boardImage = new BoardImage();
            boardImage.setAQBoard(aQBoard);
            boardImageService.saveBoardImage(boardImage, boardImgFileList.get(i));
        }
        return aQBoard.getId();
    }

    @Transactional(readOnly = true)
    public Page<aQBoard> getQBoardList(QBoardSearchDTO qBoardSearchDTO, Pageable pageable){
        return qboardRepository.getQBoardList(qBoardSearchDTO, pageable);
    }

}