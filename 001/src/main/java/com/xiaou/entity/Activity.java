package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 活动实体类
 * @author xiaou
 */
@Data
@TableName("activity")
public class Activity implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 活动名称
     */
    private String name;
    
    /**
     * 活动描述
     */
    private String description;
    
    /**
     * 活动地点
     */
    private String location;
    
    /**
     * 活动时间
     */
    private LocalDateTime activityTime;
    
    /**
     * 报名开始时间
     */
    private LocalDateTime signupStartTime;
    
    /**
     * 报名结束时间
     */
    private LocalDateTime signupEndTime;
    
    /**
     * 人数限制
     */
    private Integer maxParticipants;
    
    /**
     * 当前报名人数
     */
    private Integer currentParticipants;
    
    /**
     * 活动封面图
     */
    private String coverImage;
    
    /**
     * 发布者ID
     */
    private Long publisherId;
    
    /**
     * 发布者姓名
     */
    @TableField(exist = false)
    private String publisherName;
    
    /**
     * 活动状态（0-未开始，1-报名中，2-已结束）
     */
    private Integer status;
    
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

