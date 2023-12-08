package com.ditto.service;

import com.ditto.entity.Member;
import com.ditto.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional //로직을 처리하다가 에러가발생하면 이전상태로 콜백시켜준다.
@RequiredArgsConstructor //final 이나 @NonNull이 붙은 필드에 생성자를 생성해준다.
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
        }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByMemberId(member.getMemberId());
        //이미 가입된 회원일 경우 예외발생
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원 입니다.");
        }
    }
}
