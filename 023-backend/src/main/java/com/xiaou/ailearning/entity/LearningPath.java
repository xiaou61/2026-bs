package com.xiaou.ailearning.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("learning_path")
public class LearningPath {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private String pathName;
    
    private Long targetKnowledgePointId;
    
    private String pathSteps;
    
    private Integer estimatedDuration;
    
    private Integer currentStep;
    
    private Double progressRate;
    
    private String algorithmType;
    
    private Double optimizationScore;
    
    private Boolean isActive;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}