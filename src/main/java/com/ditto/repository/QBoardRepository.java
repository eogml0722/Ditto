package com.ditto.repository;

import com.ditto.entity.aQBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QBoardRepository extends JpaRepository<aQBoard, Long>, QuerydslPredicateExecutor<aQBoard>, QBoardRepositoryCustom {

}
