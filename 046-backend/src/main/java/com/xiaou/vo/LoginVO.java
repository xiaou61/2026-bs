package com.xiaou.vo;

import lombok.Data;

@Data
public class LoginVO {
    private Long id;
    private String username;
    private String realName;
    private String phone;
    private String avatar;
    private Integer role;
    private Long communityId;
    private Integer points;
    private String token;
}
