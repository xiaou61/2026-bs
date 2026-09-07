package com.xiaou.wedding.vo;

import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String username;
    private String realName;
    private String role;
    private String token;
}
