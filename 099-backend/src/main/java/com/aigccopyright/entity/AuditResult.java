package com.aigccopyright.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("audit_result")
public class AuditResult {
    private Long id;
    private Long taskId;
    private Long assetId;
    private String matchedRules;
    private String riskLevel;
    private BigDecimal score;
    private String conclusion;
    private String suggestion;
    private Integer reviewStatus;
    private String reviewComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
