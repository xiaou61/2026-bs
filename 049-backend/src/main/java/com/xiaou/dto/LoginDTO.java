package com.xiaou.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;
    private String code; // 微信登录code
}
