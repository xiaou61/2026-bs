package com.xiaou.ailearning.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("knowledge_relation")
public class KnowledgeRelation {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long fromKnowledgeId;
    
    private Long toKnowledgeId;
    
    private Integer relationType;
    
    private Double weight;
    
    private Double confidence;
    
    private LocalDateTime createTime;
}