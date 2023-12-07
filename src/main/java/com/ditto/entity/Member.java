package com.ditto.entity;

import com.ditto.constant.Role;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
public class Member extends BaseEntity{

    @Id
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private int phoneNum;

    private String address;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    //엔티티 생성 메서드 추가할 것
}
