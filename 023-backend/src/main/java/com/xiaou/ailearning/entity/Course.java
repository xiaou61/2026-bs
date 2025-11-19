package com.xiaou.ailearning.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 课程实体类
 */
@Data
@TableName("course")
public class Course {
    
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    private String courseName;
    
    private String courseCode;
    
    private String description;
    
    private Long categoryId;
    
    /**
     * 难度等级：1-5
     */
    private Integer difficultyLevel;
    
    /**
     * 预估学习时长(分钟)
     */
    private Integer durationMinutes;
    
    /**
     * 前置课程ID列表(JSON格式)
     */
    private String prerequisiteCourses;
    
    /**
     * 学习目标
     */
    private String learningObjectives;
    
    /**
     * 内容类型：1-视频，2-文档，3-交互，4-测试
     */
    private Integer contentType;
    
    /**
     * 状态：1-发布，0-下架
     */
    private Integer status;
    
    private LocalDateTime createTime;
}