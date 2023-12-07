package com.ditto.entity;

import com.ditto.constant.Role;
import com.ditto.dto.MemberFormDTO;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter @Setter @ToString
public class Member extends BaseEntity{

    @Id
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private int phoneNum1;

    private int phoneNum2;

    private String address;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    //엔티티 생성 메서드 추가할 것
    public static Member createMember(MemberFormDTO memberFormDTO,
                                      PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setId(memberFormDTO.getId());
        member.setName(memberFormDTO.getName());
        member.setPhoneNum1(memberFormDTO.getPhoneNum1());
        member.setPhoneNum2(memberFormDTO.getPhoneNum2());
        member.setAddress(memberFormDTO.getAddress());
        member.setEmail(memberFormDTO.getEmail());
        member.setRole(Role.USER);

        //패스워드에 관한 인코더
        String password = passwordEncoder.encode(memberFormDTO.getPassword());
        member.setPassword(password);

        return member;


    }
}
