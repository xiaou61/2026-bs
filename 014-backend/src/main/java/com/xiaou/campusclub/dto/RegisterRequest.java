package com.xiaou.campusclub.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String studentId;
    private String realName;
    private String email;
    private String major;
    private Integer grade;
}

