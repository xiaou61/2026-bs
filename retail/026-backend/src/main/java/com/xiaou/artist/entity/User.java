package com.xiaou.artist.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private String nickname;
    private String bio;
    private String role;  // USER/ARTIST/ADMIN
    private String status;  // ACTIVE/BANNED
    private BigDecimal balance;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
