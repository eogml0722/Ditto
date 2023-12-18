package com.ditto.service;

import com.ditto.constant.AskStatus;
import com.ditto.dto.AskBoardImageDTO;
import com.ditto.dto.AskBoardSearchDTO;
import com.ditto.dto.BoardWriteDTO;
import com.ditto.entity.AskBoard;
import com.ditto.entity.AskBoardImage;
import com.ditto.repository.AskBoardRepository;
import com.ditto.repository.BoardImageRepository;
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
public class AskBoardService {
    private final AskBoardRepository askBoardRepository;
    private final BoardImageService boardImageService;
    private final BoardImageRepository boardImageRepository;
    private final FileService fileService;
    public Long writeBoard(BoardWriteDTO boardWriteDTO, List<MultipartFile> boardImgFileList) throws Exception{
        boardWriteDTO.setAskStatus(AskStatus.NOT_ASK);
        AskBoard askBoard = boardWriteDTO.writeAskBoard();
        askBoardRepository.save(askBoard);

        AskBoardImage askBoardImage = new AskBoardImage();
        askBoardImage.setAskBoard(askBoard);
        boardImageService.saveBoardImage(askBoardImage, boardImgFileList.get(0));
        return askBoard.getId();
    }

    @Transactional(readOnly = true)
    public BoardWriteDTO getAskBoardDetail(Long askboardId){
        List<AskBoardImage> askBoardImageList = boardImageRepository.findByAskBoardIdOrderByIdAsc(askboardId);
        List< AskBoardImageDTO> askBoardImageDTOList = new ArrayList<>();
        for (AskBoardImage askBoardImage : askBoardImageList) {
            AskBoardImageDTO askBoardImageDTO = AskBoardImageDTO.of(askBoardImage);
            askBoardImageDTOList.add(askBoardImageDTO);
        }

        AskBoard askBoard = askBoardRepository.findById(askboardId).orElseThrow(EntityNotFoundException::new);
        BoardWriteDTO boardWriteDTO = BoardWriteDTO.of(askBoard);
        boardWriteDTO.setAskBoardImageDTO(askBoardImageDTOList);
        return boardWriteDTO;
    }

    public Long updateAskBoard(BoardWriteDTO boardWriteDTO, List<MultipartFile> boardImgFileList) throws Exception{
        AskBoard askBoard = askBoardRepository.findById(boardWriteDTO.getId()).orElseThrow(EntityNotFoundException::new);
        askBoard.updateAskBoard(boardWriteDTO);

        AskBoardImageDTO askBoardImageDTO = boardImageRepository.findByAskBoardId(askBoard.getId());
        Long id = askBoardImageDTO.getId();

        boardImageService.updateBoardImage(id, boardImgFileList.get(0));
        return askBoard.getId();
    }

    @Transactional(readOnly = true)
    public Page<AskBoard> getAskBoardList(AskBoardSearchDTO askBoardSearchDTO, Pageable pageable){
        return askBoardRepository.getAskBoardList(askBoardSearchDTO, pageable);
    }

    @Transactional
    public void deleteAskBoard(Long id) {
        AskBoard askBoard = askBoardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다!");
        });
        AskBoardImageDTO askBoardImageDTO = boardImageRepository.findByAskBoardId(id);
        Long imgid = askBoardImageDTO.getId();
        String sname = askBoardImageDTO.getSname();
        String url = "D:/ditto/board/" + sname;
        System.out.println(url);
        try{
            fileService.deleteFile(url);
        } catch (Exception e) {

        }
        boardImageRepository.deleteById(imgid);
        askBoardRepository.deleteById(id);
    }


}
