package com.xiaou.seniorhealth.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("sys_user")
public class SysUser {
    @Id
    private Long id;
    private String username;
    private String password;
    private String role;
    private Integer status;
    private LocalDateTime createdAt;
}
