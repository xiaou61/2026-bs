package com.opera.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUser {
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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


