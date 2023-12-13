package com.ditto.repository;

import com.ditto.constant.BoardCategory;
import com.ditto.entity.Board;
import com.ditto.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    //작성자의 글 목록 보기
    @Query("SELECT b FROM Board b WHERE (:member IS NULL OR b.member = :member)" +
            " AND(:title IS NULL OR b.title = :title)" +
            " AND(:content IS NULL OR b.content Like %:content%)")
    List<Board> findByMember(@Param("member") Member member,
                          @Param("title")String title,
                          @Param("content")String content);



    //업데이트
    @Query("UPDATE Board b " +
            " SET b.title = :title," +
            " b.content = :content," +
            " b.boardCategory = :boardCategory" +
            " WHERE b.id = :id")
    @Modifying(clearAutomatically = true) //영속성 컨텍스트를 비워줌으로 DB에서 불러올때 일치시킴
    void updateById(@Param("title")String title,
                    @Param("content")String content,
                    @Param("boardCategory")BoardCategory boardCategory,
                    @Param("id")Long id);


//    @Query(value = "SELECT * FROM Board b WHERE b.id < ?1 order by b.id DESC limit 1", nativeQuery = true)
//    @Query(value = "SELECT c FROM (SELECT b FROM Board b WHERE b.board_id < 1 ORDER BY b.board_id DESC) c WHERE c.ROWNUM <= 1")
//    @Query(value = "SELECT b FROM Board b WHERE b.board_id < ?1 ORDER BY b.board_id DESC")
//    Optional<Board> findLatestBoard(Long id);

//    @Query("SELECT b FROM Board b WHERE b.id > :id")
//    Optional<Board> getNext(@Param("id") Long id);
}
