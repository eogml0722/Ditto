package com.ditto.entity;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserAccount extends User {
    @Getter
    private final Member member;

    public UserAccount(Member member) {
        super(member.getMemberId(), member.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.member = member;
    }
}
