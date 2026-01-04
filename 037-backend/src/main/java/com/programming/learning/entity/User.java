package com.programming.learning.entity;

import com.programming.learning.enums.UserRole;
import com.programming.learning.enums.UserStatus;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体
 */
@Data
public class User {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 微信openId
     */
    private String openId;

    /**
     * 微信unionId
     */
    private String unionId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 角色
     */
    private UserRole role;

    /**
     * 状态
     */
    private UserStatus status;

    /**
     * 积分
     */
    private Integer score;

    /**
     * 等级（1-5）
     */
    private Integer level;

    /**
     * 个人简介
     */
    private String bio;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
