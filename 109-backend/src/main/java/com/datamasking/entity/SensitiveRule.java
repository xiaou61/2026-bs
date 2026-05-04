package com.datamasking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sensitive_rule")
public class SensitiveRule {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String ruleName;
    private String ruleType;
    private String matchPattern;
    private String riskLevel;
    private String maskingType;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
