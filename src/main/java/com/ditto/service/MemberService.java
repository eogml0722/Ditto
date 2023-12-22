package com.ditto.service;

import com.ditto.constant.OAuthType;
import com.ditto.dto.MemberFormDTO;
import com.ditto.entity.Member;
import com.ditto.entity.UserAccount;
import com.ditto.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional //로직을 처리하다가 에러가발생하면 이전상태로 콜백시켜준다.
@RequiredArgsConstructor //final 이나 @NonNull이 붙은 필드에 생성자를 생성해준다.
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;


    public Member saveMember(Member member){
        validateDuplicateMember(member);

        if (member.getOauth() == null) {
            member.setOauth(OAuthType.DITTO);
        }

        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByMemberId(member.getMemberId());
        Member findEmailMember = memberRepository.findByEmail(member.getEmail());
        //이미 가입된 회원일 경우 예외발생
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원 입니다.");
        }

        if(findEmailMember != null) {
            throw new IllegalStateException("해당 이메일로 가입된 회원이 존재합니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws
            UsernameNotFoundException { //로그인할 유저의 id를 파라미터로 전달받음
        Member member = memberRepository.findByMemberId(memberId);

        if(member == null) {
            throw new UsernameNotFoundException(memberId + "is not found.");
        }

        return User.builder()
                .username(member.getMemberId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public boolean deleteMember(String id, String password) {
        Member member = memberRepository.findByMemberId(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(password, member.getPassword())){
            memberRepository.deleteById(member.getMemberId());
            return true;
        }
        return false;
    }

    //아이디찾기
    public Member findId(String name, String email){
        return memberRepository.findByNameAndEmail(name, email);
        }
    public Member detailMember(String id){
        Member member = memberRepository.findByMemberId(id);
        if(member == null){
            throw new IllegalStateException("존재하지 않는 회원입니다?");
        }
        return member;
    }

    public void sendLoginLink(Member member) {
        member.generateToken();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(member.getEmail());
        mailMessage.setSubject("[Ditto] 로그인 링크");
        mailMessage.setText("/members/login-by-email?token=" + member.getEmailToken() + "&email=" + member.getEmail());
        mailSender.send(mailMessage);
    }
    public Member updateMember(Member member){
        String id = memberRepository.findByMemberId(member.getMemberId()).getMemberId();
        member.setMemberId(id);
        member.setName(member.getName());
        member.setPhoneNum(member.getPhoneNum());
        member.setZipcode(member.getZipcode());
        member.setStreetAddress(member.getStreetAddress());
        member.setDetailAddress(member.getDetailAddress());
        member.setPassword(member.getPassword());
        member.setEmail(member.getEmail());
        return memberRepository.save(member);
    }

    public MemberFormDTO updatePassword(MemberFormDTO member, String password){

        Member memberx = memberRepository.findByMemberId(member.getMemberId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(encoder.matches(password, memberx.getPassword())) {
            String id = memberRepository.findByMemberId(member.getMemberId()).getMemberId();

            Member oldMember = memberRepository.findByMemberId(id);
            member.setMemberId(id);
            member.setPassword(member.getPassword());
            member.setName(oldMember.getName());
            member.setPhoneNum(oldMember.getPhoneNum());
            member.setZipcode(oldMember.getZipcode());
            member.setStreetAddress(oldMember.getStreetAddress());
            member.setDetailAddress(oldMember.getDetailAddress());
            member.setEmail(oldMember.getEmail());
            return member;
        }
        return null;
    }

    public void login(Member member) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(new UserAccount(member),
                member.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token); // AuthenticationManager를 쓰는 방법이 정석적인 방법
    }

}




