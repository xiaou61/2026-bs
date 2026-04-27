package com.xiaou.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class LoginDTO {
    private String username;
    @ToString.Exclude
    private String password;
    private String code; // 微信登录code
}
