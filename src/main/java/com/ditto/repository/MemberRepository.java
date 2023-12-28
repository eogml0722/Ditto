package com.ditto.repository;

import com.ditto.dto.MemberFormDTO;
import com.ditto.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    

    Member findByMemberId(String id);
    //아이디찾기시 이름과 이메일에 해당하는 아이디가 있는지 검사
    Member findByNameAndEmail(String name, String email);

    Member findByMemberIdAndNameAndEmail(String Id, String name, String email);

    Member findByEmail(String email);
//회원가입시 중복된 회원이 있는지 id로 검사

    Page<Member> findAll(Pageable pageable);

}
