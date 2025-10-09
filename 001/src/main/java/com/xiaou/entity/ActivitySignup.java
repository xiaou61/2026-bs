package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
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
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;
}

