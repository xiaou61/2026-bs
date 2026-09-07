package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("competition")
public class Competition {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private Long categoryId;
    private String cover;
    private String description;
    private String requirement;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime submitDeadline;
    private Integer status;
    private Integer maxWords;
    private Integer minWords;
    private Integer publishResult;
    private Long createUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String categoryName;
    @TableField(exist = false)
    private Integer workCount;
    @TableField(exist = false)
    private Integer participantCount;
}
