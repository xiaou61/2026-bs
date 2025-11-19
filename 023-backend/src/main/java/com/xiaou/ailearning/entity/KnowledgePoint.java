package com.xiaou.ailearning.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("knowledge_point")
public class KnowledgePoint {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String pointName;
    
    private String pointCode;
    
    private Long courseId;
    
    private Long parentId;
    
    private String description;
    
    private Integer difficultyLevel;
    
    private Double importanceScore;
    
    private String keywords;
    
    private Integer learningTimeEstimate;
    
    private LocalDateTime createTime;
}