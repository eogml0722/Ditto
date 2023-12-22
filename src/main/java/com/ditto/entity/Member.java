package com.ditto.entity;

import com.ditto.constant.OAuthType;
import com.ditto.constant.Role;
import com.ditto.dto.MemberFormDTO;
import com.ditto.repository.MemberRepository;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="member")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member extends AuditingEntity{

    @Id
    @Column(name = "member_id")
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

    private String emailToken;

    private LocalDateTime emailTokenGeneratedAt;

    @OneToMany(mappedBy = "member" , fetch = FetchType.LAZY ,
            cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy="member", cascade = CascadeType.ALL
            ,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AskBoard> askboards = new ArrayList<>();

    @OneToMany(mappedBy="member", cascade = CascadeType.ALL
            ,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AskComment> askComments = new ArrayList<>();

    // Member 엔티티를 생성하는 메소드
    public static Member createMember(MemberFormDTO memberFormDTO,
                                      PasswordEncoder passwordEncoder) {

        Member member = new Member();
        member.setMemberId(memberFormDTO.getMemberId());
        member.setName(memberFormDTO.getName());
        member.setPhoneNum(memberFormDTO.getPhoneNum());
        member.setEmail(memberFormDTO.getEmail());
        member.setRole(Role.ADMIN);

        member.setZipcode(memberFormDTO.getZipcode());
        member.setStreetAddress(memberFormDTO.getStreetAddress());
        member.setDetailAddress(memberFormDTO.getDetailAddress());

        //비밀번호를 암호화 한다.
        String password = passwordEncoder.encode(memberFormDTO.getPassword());
        member.setPassword(password);

        return member;
    }

    public static Member testMember(PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setMemberId("test12");
        member.setName("김그린");
        member.setPhoneNum("010-1234-5678");
        member.setEmail("test@mail.com");
        member.setRole(Role.ADMIN);

        member.setZipcode("12345");
        member.setStreetAddress("12345");
        member.setDetailAddress("주소");
        String password = passwordEncoder.encode("xptmxm12!");
        member.setPassword(password);

        return member;
    }

    public void generateToken() {
        this.emailToken = UUID.randomUUID().toString();
        this.emailTokenGeneratedAt = LocalDateTime.now();
    }
    public boolean enableToSendEmail() {
        return this.emailTokenGeneratedAt.isBefore(LocalDateTime.now().minusMinutes(5));
    }

    public boolean isValid(String token) {
        return this.emailToken.equals(token);
    }

}
