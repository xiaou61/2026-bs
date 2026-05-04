package com.devopsrelease.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RollbackPlan {
    private Long id;
private Long planId;
private Long serviceId;
private String rollbackVersion;
private String strategyName;
private String triggerCondition;
private String ownerName;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
