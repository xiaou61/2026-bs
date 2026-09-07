package com.xiaou.community.entity;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role; // ADMIN, OWNER
    private Date createTime;
}
