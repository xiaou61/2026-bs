package com.xiaou.bike.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户信息VO
 */
@Data
public class UserInfoVO {

    private Long id;

    private String username;

    private String realName;

    private String phone;

    private String studentId;

    private String avatar;

    private Integer gender;

    private String email;

    /**
     * 信用分
     */
    private Integer creditScore;

    /**
     * 认证状态
     */
    private Integer authStatus;

    /**
     * 钱包余额
     */
    private BigDecimal balance;

    /**
     * 押金
     */
    private BigDecimal deposit;

    /**
     * 押金状态
     */
    private Integer depositStatus;

    /**
     * 注册时间
     */
    private LocalDateTime createTime;
}
