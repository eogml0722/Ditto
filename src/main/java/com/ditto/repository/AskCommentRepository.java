package com.ditto.repository;

import com.ditto.dto.CommentWriteDTO;
import com.ditto.entity.AskComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AskCommentRepository extends JpaRepository <AskComment, Long>  {
    AskComment findByAskBoardId(Long askboardId);
}
