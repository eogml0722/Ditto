package com.ditto.repository;

import com.ditto.dto.AskBoardImageDTO;
import com.ditto.dto.AskBoardSearchDTO;
import com.ditto.dto.CommentWriteDTO;
import com.ditto.entity.AskBoard;
import com.ditto.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.ArrayList;
import java.util.List;

public interface AskBoardRepository extends JpaRepository<AskBoard, Long>, QuerydslPredicateExecutor<AskBoard>, AskBoardRepositoryCustom{
    ArrayList<AskBoard> findByMemberOrderByIdDesc(Member member);
}