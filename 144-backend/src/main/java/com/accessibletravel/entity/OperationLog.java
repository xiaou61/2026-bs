package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OperationLog {
    private Long id;
    private String operatorName;
    private String moduleName;
    private String actionType;
    private String targetName;
    private String detailInfo;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}





