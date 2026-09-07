package com.worksite.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OperationLog {
    private Long id;
    private String operatorName;
    private String moduleName;
    private String actionType;
    private String targetName;
    private String detail;
    private String ipAddress;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
