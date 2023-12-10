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
    @Column(name = "member_id", unique = true) //회원 id값을 통해 유일하게 구분해야하기때문에 동일한 값이 들어올 수 없도록 unique속성을 지정
    private String memberId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNum1;

    @Column(nullable = false)
    private String phoneNum2;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING) //enum의 순서가 바뀔경우를 대비해 옵션을 String으로 지정
    private Role role;
    
    // Member 엔티티를 생성하는 메소드
    public static Member createMember(MemberFormDTO memberFormDTO,
                                      PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setMemberId(memberFormDTO.getMemberId());
        member.setName(memberFormDTO.getName());
        member.setPhoneNum1(memberFormDTO.getPhoneNum1());
        member.setPhoneNum2(memberFormDTO.getPhoneNum2());
        member.setAddress(memberFormDTO.getAddress());
        member.setEmail(memberFormDTO.getEmail());
        member.setRole(Role.USER);

        //비밀번호를 암호화 한다.
        String password = passwordEncoder.encode(memberFormDTO.getPassword());
        member.setPassword(password);

        return member;


    }
}
