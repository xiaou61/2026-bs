package com.zerotrust.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PolicyRule {
    private Long id;
    private String ruleName;
    private String policyName;
    private String conditionType;
    private String actionType;
    private Integer priority;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
