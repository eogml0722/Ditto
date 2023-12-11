package com.ditto.service;


import com.ditto.dto.BoardImageDTO;
import com.ditto.dto.BoardWriteDTO;
import com.ditto.entity.BoardImage;
import com.ditto.entity.QBoard;
import com.ditto.repository.BoardImageRepository;
import com.ditto.repository.QboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QBoardService {

    private final QboardRepository qboardRepository;
    private final BoardImageService boardImageService;
    private final BoardImageRepository boardImageRepository;

    public Long writeBoard(BoardWriteDTO boardWriteDTO, List<MultipartFile> boardImgFileList) throws Exception{
        QBoard qBoard = boardWriteDTO.createQBoard();
        qboardRepository.save(qBoard);
        System.out.println("qboardservice :" + boardImgFileList );
        for(int i=0;i<boardImgFileList.size();i++){
            BoardImage boardImage = new BoardImage();
            boardImage.setQBoard(qBoard);
            boardImageService.saveBoardImage(boardImage, boardImgFileList.get(i));
        }
        return qBoard.getId();
    }

//    @Transactional(readOnly = true)
//    public BoardWriteDTO getBoardList(Long qboardId){
//        List<BoardImage> boardImageList = boardImageRepository.findByQboardIdOrderByIdAsc(qboardId);
//        List<BoardImageDTO> boardImageDTOList = new ArrayList<>();
//        for(BoardImage boardImage : boardImageList) {
//            BoardImageDTO boardImageDTO = BoardImageDTO.of(boardImage);
//            boardImageDTOList.add(boardImageDTO);
//        }
//        QBoard qBoard = qboardRepository.findById(qboardId).orElseThrow(EntityNotFoundException::new);
//        BoardWriteDTO boardWriteDTO= BoardWriteDTO.of(qBoard);
//        boardWriteDTO.setBoardImageDTOList(boardImageDTOList);
//        return boardWriteDTO;
//    }

}