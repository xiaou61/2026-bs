package com.xiaou.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class RegisterDTO {
    private String username;
    @ToString.Exclude
    private String password;
    private String nickname;
    private String phone;
    private String targetSchool;
    private String targetMajor;
    private Integer examYear;
}
