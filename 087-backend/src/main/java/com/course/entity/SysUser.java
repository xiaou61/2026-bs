package com.course.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUser {
    private Long id;
    private String username;
    private String password;
    private String realName;
    private String phone;
    private String role;
    private Long departmentId;
    private Long majorId;
    private Long classId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
