package com.ditto.repository;

import com.ditto.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository {
    List<Img> findByIdOrderByIdAsc(Long id);
}
