package com.hospital.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SysUser {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String phone;
    private String email;
    private String gender;
    private Integer age;
    private String role;
    private Integer status;
    private BigDecimal balance;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
