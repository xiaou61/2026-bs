package com.xiaou.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String realName;
    private String phone;
    private Long elderId;  // 家属关联的老人ID
}
