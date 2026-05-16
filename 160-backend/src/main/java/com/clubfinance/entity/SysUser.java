package com.clubfinance.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUser {
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String nickname;
    private String role;
    private String department;
    private String phone;
    private String email;
    private Integer status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
