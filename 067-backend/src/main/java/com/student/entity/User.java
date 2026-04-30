package com.student.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String nickname;
    private String phone;
    private String email;
    private String role;
    private Integer status;
    private String college;
    private String major;
    private String grade;
    private LocalDateTime lastLoginTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
