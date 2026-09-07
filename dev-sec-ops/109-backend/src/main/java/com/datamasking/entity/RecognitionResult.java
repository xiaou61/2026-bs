package com.datamasking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("recognition_result")
public class RecognitionResult {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String resultNo;
    private String taskNo;
    private String fieldName;
    private String sensitiveType;
    private String sampleValue;
    private BigDecimal confidence;
    private String riskLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
