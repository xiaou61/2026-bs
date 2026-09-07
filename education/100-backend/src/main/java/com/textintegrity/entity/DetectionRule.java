package com.textintegrity.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DetectionRule {
    private Long id;
    private String ruleName;
    private String ruleType;
    private String riskLevel;
    private BigDecimal weight;
    private String keywords;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
