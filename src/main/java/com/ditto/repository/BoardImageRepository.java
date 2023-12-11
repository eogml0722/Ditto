package com.ditto.repository;

import com.ditto.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
//    List<BoardImage> findByQboardIdOrderByIdAsc(Long qboardId);
}
