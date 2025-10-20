package com.xiaou.express.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String studentId;
    private String username;
    private String phone;
    private String password;
    private String realName;
    private String dormitoryBuilding;
    private String dormitoryRoom;
}

