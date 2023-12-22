package com.ditto.config;

import com.ditto.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemberPrincipalDetails implements UserDetails {

    private final Member member;

    //member 패키지에 선언해놓은 member 엔티티를 사용하기 위해 선언
    public MemberPrincipalDetails(Member member) {
        this.member = member;
    }
    //생성자
    public Member getMember(){
        return member;
    }
    
    //member 계정의 권한을 담아두기 위해
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().toString()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
