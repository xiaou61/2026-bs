package com.xiaou.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    private String phone;
    private String email;
    @NotNull(message = "请选择角色")
    private Integer role;  // 0-游客 1-房东
}
