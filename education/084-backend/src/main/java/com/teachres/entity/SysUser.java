package com.teachres.entity;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
public class SysUser {
    private Long id;
    private String username;
    @JsonIgnore
    @ToString.Exclude
    private String password;
    private String realName;
    private String phone;
    private String role;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
