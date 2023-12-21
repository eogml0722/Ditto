package com.ditto.repository;

import com.ditto.dto.AskBoardSearchDTO;
import com.ditto.entity.AskBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AskBoardRepositoryCustom {
    Page<AskBoard> getAskBoardList(AskBoardSearchDTO askBoardSearchDTO, Pageable pageable);

}
