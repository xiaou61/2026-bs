package com.xiaou.ticket.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String phone;
    private String email;
    private String realName;
    private String idCard;
}
