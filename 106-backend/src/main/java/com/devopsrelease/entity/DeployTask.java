package com.devopsrelease.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeployTask {
    private Long id;
private Long orderId;
private Long serviceId;
private Long envId;
private String taskName;
private String executor;
private String status;
private String startedAt;
private String finishedAt;
private String logSummary;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
