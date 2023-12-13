package com.ditto.service;

import com.ditto.constant.OAuthType;
import com.ditto.dto.MemberFormDTO;
import com.ditto.entity.Member;
import com.ditto.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional //로직을 처리하다가 에러가발생하면 이전상태로 콜백시켜준다.
@RequiredArgsConstructor //final 이나 @NonNull이 붙은 필드에 생성자를 생성해준다.
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        validateDuplicateMember(member);

        if (member.getOauth() == null) {
            member.setOauth(OAuthType.DITTO);
        }

        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByMemberId(member.getMemberId());
        //이미 가입된 회원일 경우 예외발생
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원 입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws
            UsernameNotFoundException { //로그인할 유저의 id를 파라미터로 전달받음
        Member member = memberRepository.findByMemberId(memberId);

        System.out.println(member);
        System.out.println(memberId);

        if(member == null) {
            throw new UsernameNotFoundException(memberId);
        }

        return User.builder() //User 객체 생성을 위해 회원의 아이디,비밀번호, role을 파라미터로 넘겨줌
                .username(member.getMemberId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
//    @Override
//    public MemberFormDTO getMemberDetails(String memberId) {
//        return memberRepository.getMemberDetails(memberId);
//    }


}
