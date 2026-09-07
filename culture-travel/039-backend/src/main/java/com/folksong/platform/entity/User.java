package com.folksong.platform.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Table("users")
public class User {
    @Id
    @Column("id")
    private Long id;
    @Column("username")
    private String username;
    @Column("password")
    private String password;
    @Column("nickname")
    private String nickname;
    @Column("avatar")
    private String avatar;
    @Column("email")
    private String email;
    @Column("phone")
    private String phone;
    @Column("gender")
    private Integer gender;
    @Column("introduction")
    private String introduction;
    @Column("role")
    private Integer role;
    @Column("status")
    private Integer status;
    @Column("create_time")
    private LocalDateTime createTime;
    @Column("update_time")
    private LocalDateTime updateTime;
}
