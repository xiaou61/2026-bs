package com.groupbuy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("group_order")
public class GroupOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long activityId;
    private Long leaderId;
    private Integer currentCount;
    private Integer targetCount;
    private Integer status;
    private LocalDateTime expireTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private GroupActivity activity;
    @TableField(exist = false)
    private String leaderName;
    @TableField(exist = false)
    private String leaderAvatar;
}
