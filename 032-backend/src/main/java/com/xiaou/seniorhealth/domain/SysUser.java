package com.xiaou.seniorhealth.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("sys_user")
public class SysUser {
    @Id
    @Column("id")
    private Long id;
    @Column("username")
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column("password")
    private String password;
    @Column("role")
    private String role;
    @Column("status")
    private Integer status;
    @Column("created_at")
    private LocalDateTime createdAt;
}
