package com.ditto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberFormDTO {

    private String memberId;
    private String password;
    private String name;
    private String phoneNum1;
    private String phoneNum2;
    private String address;
    private String email;
}
