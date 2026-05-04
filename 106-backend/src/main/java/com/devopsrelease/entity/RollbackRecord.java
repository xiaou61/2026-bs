package com.devopsrelease.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RollbackRecord {
    private Long id;
private Long orderId;
private Long planId;
private String rollbackNo;
private String operatorName;
private String reason;
private String status;
private String startedAt;
private String finishedAt;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
