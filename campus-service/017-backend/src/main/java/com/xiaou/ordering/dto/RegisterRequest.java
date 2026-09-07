package com.xiaou.ordering.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String studentId;
    private String username;
    private String phone;
    private String password;
    private String realName;
    private String department;
    private String major;
    private String dormitory;
}

