package com.xiaou.bike.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（加密）
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 学号/工号
     */
    private String studentId;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 性别：0未知 1男 2女
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 信用分
     */
    private Integer creditScore;

    /**
     * 状态：0禁用 1正常
     */
    private Integer status;

    /**
     * 认证状态：0未认证 1待审核 2已认证 3认证失败
     */
    private Integer authStatus;

    /**
     * 认证图片
     */
    private String authImage;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
