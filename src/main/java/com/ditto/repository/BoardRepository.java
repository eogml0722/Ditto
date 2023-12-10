package com.ditto.repository;

import com.ditto.entity.Board;
import com.ditto.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    //작성자의 글 목록 보기
    @Query("SELECT b FROM Board b WHERE (:member IS NULL OR b.member = :member)" +
            " AND(:title IS NULL OR b.title = :title)" +
            " AND(:content IS NULL OR b.content Like %:content%)")
    List<Board> findByMember(@Param("member") Member member,
                             @Param("title")String title,
                             @Param("content")String content);
}
