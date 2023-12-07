package com.ditto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberFormDTO {

    private long id;
    private String password;
    private String name;
    private int phoneNum1;
    private int phoneNum2;
    private String address;
    private String email;
}
