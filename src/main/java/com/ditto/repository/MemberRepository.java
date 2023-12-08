package com.ditto.repository;

import com.ditto.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    
    //회원가입시 중복된 회원이 있는지 id로 검사
    Member findByMemberId(String id);
}