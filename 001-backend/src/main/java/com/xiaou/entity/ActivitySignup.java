package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 活动报名实体类
 * @author xiaou
 */
@Data
@TableName("activity_signup")
public class ActivitySignup implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 活动ID
     */
    private Long activityId;
    
    /**
     * 学生ID
     */
    private Long studentId;
    
    /**
     * 学生姓名
     */
    @TableField(exist = false)
    private String studentName;
    
    /**
     * 学生手机号
     */
    @TableField(exist = false)
    private String phone;
    
    /**
     * 学生邮箱
     */
    @TableField(exist = false)
    private String email;
    
    /**
     * 报名时间（显示用）
     */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime signupTime;
    
    /**
     * 报名状态（0-待审核，1-已通过，2-已取消）
     */
    private Integer status;
    
    /**
     * 报名备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    
    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;
}

