package com.xiaou.rice.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String phone;
    private String nickname;
    @NotNull
    private Integer role; // 1农户 2机手
}
