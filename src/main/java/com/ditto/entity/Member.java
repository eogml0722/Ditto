package com.ditto.entity;

import com.ditto.constant.OAuthType;
import com.ditto.constant.Role;
import com.ditto.dto.MemberFormDTO;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity{

    @Id
    @Column(name = "member_id", unique = true) //회원 id값을 통해 유일하게 구분해야하기때문에 동일한 값이 들어올 수 없도록 unique속성을 지정
    private String memberId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNum;

    private String zipcode; //우편번호

    private String streetAddress; //도로명주소

    private String detailAddress; //상세주소

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING) //enum의 순서가 바뀔경우를 대비해 옵션을 String으로 지정
    private Role role;

    @Enumerated(EnumType.STRING)
    private OAuthType oauth;

    // Member 엔티티를 생성하는 메소드
    public static Member createMember(MemberFormDTO memberFormDTO,
                                      PasswordEncoder passwordEncoder) {

        Member member = new Member();
        member.setMemberId(memberFormDTO.getMemberId());
        member.setName(memberFormDTO.getName());
        member.setPhoneNum(memberFormDTO.getPhoneNum());
        member.setEmail(memberFormDTO.getEmail());
        member.setRole(Role.USER);

        member.setZipcode(memberFormDTO.getZipcode());
        member.setStreetAddress(memberFormDTO.getStreetAddress());
        member.setDetailAddress(memberFormDTO.getDetailAddress());

        //비밀번호를 암호화 한다.
        String password = passwordEncoder.encode(memberFormDTO.getPassword());
        member.setPassword(password);

        return member;


    }
}
