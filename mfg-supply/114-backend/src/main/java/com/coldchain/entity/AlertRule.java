package com.coldchain.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AlertRule {
    private Long id;
    private String ruleName;
    private String ruleCode;
    private String ruleType;
    private Integer thresholdValue;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
