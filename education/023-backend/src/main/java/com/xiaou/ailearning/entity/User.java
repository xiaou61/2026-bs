package com.xiaou.ailearning.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("user")
public class User {
    
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    private String username;
    
    private String password;
    
    private String realName;
    
    private String email;
    
    private String phone;
    
    private String grade;
    
    private String major;
    
    /**
     * 学习风格：1-视觉型，2-听觉型，3-动觉型
     */
    private Integer learningStyle;
    
    /**
     * 认知水平评分(0-1)
     */
    private Double cognitiveLevel;
    
    /**
     * 学习时间偏好
     */
    private String studyTimePreference;
    
    /**
     * 难度偏好：1-简单，2-中等，3-困难
     */
    private Integer difficultyPreference;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}