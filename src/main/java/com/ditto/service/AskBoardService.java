package com.ditto.service;

import com.ditto.constant.AskStatus;
import com.ditto.dto.AskBoardImageDTO;
import com.ditto.dto.AskBoardSearchDTO;
import com.ditto.dto.BoardWriteDTO;
import com.ditto.dto.CommentWriteDTO;
import com.ditto.entity.AskBoard;
import com.ditto.entity.AskBoardImage;
import com.ditto.entity.AskComment;
import com.ditto.entity.Member;
import com.ditto.repository.AskBoardRepository;
import com.ditto.repository.AskCommentRepository;
import com.ditto.repository.BoardImageRepository;
import com.ditto.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AskBoardService {
    private final AskBoardRepository askBoardRepository;
    private final BoardImageService boardImageService;
    private final BoardImageRepository boardImageRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final AskCommentRepository askCommentRepository;

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

        if (askCommentRepository.findByAskBoardId(id) == null) {
        } else if (askCommentRepository.findByAskBoardId(id) != null) {
            AskComment askComment = askCommentRepository.findByAskBoardId(id);
            Long commentid = askComment.getId();
            AskBoardImageDTO askBoardImageDTO = boardImageRepository.findByAskCommentId(commentid);
            Long imgid = askBoardImageDTO.getId();
            String sname = askBoardImageDTO.getSname();
            String url = "D:/ditto/board/" + sname;
            try{
                fileService.deleteFile(url);
                boardImageRepository.deleteById(imgid);
            } catch (Exception e) {
            }
        }

        AskBoard askBoard = askBoardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다!");
        });
        AskBoardImageDTO askBoardImageDTO = boardImageRepository.findByAskBoardId(id);
        Long imgid = askBoardImageDTO.getId();
        String sname = askBoardImageDTO.getSname();
        String url = "D:/ditto/board/" + sname;
        askBoardRepository.deleteById(id);
        try{
            fileService.deleteFile(url);
            boardImageRepository.deleteById(imgid);
        } catch (Exception e) {

        }

    }

    public Long writeComment(Long id, CommentWriteDTO commentWriteDTO, List<MultipartFile> boardImgFileList) throws Exception{
        // 게시판 id의 보드db를 가져와서
        AskBoard askBoard = askBoardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        // 상태를 바꿈
        askBoard.setAskStatus(AskStatus.ASK);

        AskComment askComment = commentWriteDTO.writeAskComment();
        askComment.setAskBoard(askBoard);
        askCommentRepository.save(askComment);

        AskBoardImage askBoardImage = new AskBoardImage();
        askBoardImage.setAskComment(askComment);
        boardImageService.saveBoardImage(askBoardImage, boardImgFileList.get(0));

        return askComment.getId();
    }

    @Transactional(readOnly = true)
    public CommentWriteDTO getCommentDetail(Long id){

        AskComment askCommnet = askCommentRepository.findByAskBoardId(id);

        if(askCommnet==null){
            CommentWriteDTO commentWriteDTO = new CommentWriteDTO();
            return commentWriteDTO;
        } else {
            Long askCommnetId = askCommnet.getId();
            List<AskBoardImage> askBoardImageList = boardImageRepository.findByAskCommentIdOrderByIdAsc(askCommnetId);
            List< AskBoardImageDTO> askBoardImageDTOList = new ArrayList<>();
            for (AskBoardImage askBoardImage : askBoardImageList) {
                AskBoardImageDTO askBoardImageDTO = AskBoardImageDTO.of(askBoardImage);
                askBoardImageDTOList.add(askBoardImageDTO);
            }
            AskComment askComment = askCommentRepository.findById(askCommnetId).orElseThrow(EntityNotFoundException::new);
            CommentWriteDTO commentWriteDTO = CommentWriteDTO.of(askComment);
            commentWriteDTO.setAskBoardImageDTO(askBoardImageDTOList);
            return commentWriteDTO;
        }
    }

    public Long updateComment(CommentWriteDTO commentWriteDTO, List<MultipartFile> boardImgFileList) throws Exception{

        AskComment askComment = askCommentRepository.findById(commentWriteDTO.getId()).orElseThrow(EntityNotFoundException::new);
        askComment.updateComment(commentWriteDTO);

        AskBoardImageDTO askBoardImageDTO = boardImageRepository.findByAskCommentId(commentWriteDTO.getId());
        Long imgid = askBoardImageDTO.getId();

        boardImageService.updateBoardImage(imgid, boardImgFileList.get(0));
        return askComment.getId();
    }

    public void deleteComment(Long id){
        AskBoard askBoard = askBoardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        askBoard.setAskStatus(AskStatus.NOT_ASK);

        AskComment askComment = askCommentRepository.findByAskBoardId(id);
        Long commentid = askComment.getId();

        AskBoardImageDTO askBoardImageDTO = boardImageRepository.findByAskCommentId(commentid);

        try{
            Long imgid = askBoardImageDTO.getId();
            String sname = askBoardImageDTO.getSname();
            String url = "D:/ditto/board/" + sname;
            fileService.deleteFile(url);
            boardImageRepository.deleteById(imgid);
        } catch (Exception e) {

        }
        askCommentRepository.deleteById(commentid);
    }

    @Transactional
    public boolean validateAskBoard(Long askboardid, String id){
        Member curMember = memberRepository.findByMemberId(id);
        AskBoard askBoard = askBoardRepository.findById(askboardid).orElseThrow(EntityNotFoundException::new);

        Member savedMember = askBoard.getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }
        return true;
    }


}
