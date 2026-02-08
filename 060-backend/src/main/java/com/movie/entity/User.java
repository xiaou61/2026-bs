package com.movie.entity;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String phone;
    private String email;
    private String role;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
