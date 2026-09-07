package com.twinpark.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WorkOrder {
    private Long id;
    private String workOrderNo;
    private String defectNo;
    private String deviceName;
    private String engineerName;
    private String deadlineTime;
    private String handleResult;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
