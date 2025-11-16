package com.xiaou.vo;

import lombok.Data;

@Data
public class UserLoginVO {

    private String token;
    private Long userId;
    private String username;
    private String realName;
    private String avatar;
    private Integer creditScore;
}