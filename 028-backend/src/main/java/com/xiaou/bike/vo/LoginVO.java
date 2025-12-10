package com.xiaou.bike.vo;

import lombok.Data;

/**
 * 登录VO
 */
@Data
public class LoginVO {

    /**
     * Token
     */
    private String token;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户类型：user/admin
     */
    private String userType;
}
