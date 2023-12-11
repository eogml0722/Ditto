package com.ditto.repository;

import com.ditto.entity.QBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QboardRepository extends JpaRepository<QBoard, Long> {
}
