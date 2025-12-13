package com.xiaou.health.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    private String password;
    
    private String email;
    
    private String phone;
    
    @NotBlank(message = "角色不能为空")
    private String role;
    
    private String realName;
}
