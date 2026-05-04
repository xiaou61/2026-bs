package com.devopsrelease.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReleasePlan {
    private Long id;
private String planNo;
private String planTitle;
private Long serviceId;
private Long envId;
private String releaseWindow;
private String riskLevel;
private String status;
private String description;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
