package com.ditto.repository;

import com.ditto.dto.QBoardSearchDTO;
import com.ditto.entity.aQBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QBoardRepositoryCustom {
    Page<aQBoard> getQBoardList (QBoardSearchDTO qBoardSearchDTO, Pageable pageable);
}
