package com.xiaou.ailearning.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学习记录实体类
 */
@Data
@TableName("learning_record")
public class LearningRecord {
    
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    private Long userId;
    
    private Long courseId;
    
    private Long knowledgePointId;
    
    /**
     * 学习类型：1-观看，2-练习，3-测试，4-复习
     */
    private Integer learningType;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    /**
     * 学习时长(秒)
     */
    private Integer durationSeconds;
    
    /**
     * 完成率(%)
     */
    private Double completionRate;
    
    /**
     * 掌握程度(0-1)
     */
    private Double masteryLevel;
    
    /**
     * 得分
     */
    private Double score;
    
    /**
     * 交互次数
     */
    private Integer interactionCount;
    
    /**
     * 专注时长(秒)
     */
    private Integer focusDuration;
}