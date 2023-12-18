package com.ditto.repository;

import com.ditto.dto.AskBoardImageDTO;
import com.ditto.entity.AskBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<AskBoardImage, Long> {
    List<AskBoardImage> findByAskBoardIdOrderByIdAsc(Long askboardId);
    AskBoardImageDTO findByAskBoardId(Long askboardId);
}
