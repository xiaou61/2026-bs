package com.xiaou.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String realName;
    private String phone;
    private Integer role; // 0-玩家 1-店家 2-作者
}
