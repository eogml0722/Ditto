package com.ditto.repository;

import com.ditto.entity.AskBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AskBoardRepository extends JpaRepository<AskBoard, Long>, QuerydslPredicateExecutor<AskBoard>, QBoardRepositoryCustom {

}
