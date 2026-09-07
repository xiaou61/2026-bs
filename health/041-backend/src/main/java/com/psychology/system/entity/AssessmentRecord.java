package com.psychology.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("assessment_record")
public class AssessmentRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    private Long scaleId;
    private String answers;
    private Integer totalScore;
    private String resultLevel;
    private String report;
    private String status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
