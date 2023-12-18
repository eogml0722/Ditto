package com.ditto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberFormDTO {

    private String memberId;
    private String password;
    private String name;
    private String phoneNum;
    private String zipcode; //우편번호
    private String streetAddress; //도로명주소
    private String detailAddress; //상세주소
    private String email;
}
