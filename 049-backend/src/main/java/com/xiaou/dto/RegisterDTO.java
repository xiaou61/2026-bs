package com.xiaou.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String targetSchool;
    private String targetMajor;
    private Integer examYear;
}
