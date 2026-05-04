package com.devopsrelease.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReleaseOrder {
    private Long id;
private String orderNo;
private Long planId;
private Long serviceId;
private String versionNo;
private String applicant;
private String releaseScope;
private String status;
private String scheduledAt;
private String summary;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
