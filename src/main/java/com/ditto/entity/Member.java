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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String email;

    private String password;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;





}
