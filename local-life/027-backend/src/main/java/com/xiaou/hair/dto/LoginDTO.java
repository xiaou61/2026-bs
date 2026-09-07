package com.xiaou.hair.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 用户登录DTO
 */
@Data
public class LoginDTO {
    
    @NotBlank(message = "手机号不能为空")
    private String phone;
    
    @NotBlank(message = "密码不能为空")
    private String password;
}
