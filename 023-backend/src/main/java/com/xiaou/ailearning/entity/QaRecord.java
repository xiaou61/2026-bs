package com.xiaou.ailearning.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("qa_record")
public class QaRecord {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private String question;
    
    private String questionKeywords;
    
    private String answer;
    
    private Double answerScore;
    
    private String knowledgePointIds;
    
    private String sessionId;
    
    private Boolean isHelpful;
    
    private Integer responseTimeMs;
    
    private LocalDateTime createTime;
}