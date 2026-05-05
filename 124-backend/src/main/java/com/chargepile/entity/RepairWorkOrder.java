package com.chargepile.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RepairWorkOrder {
    private Long id;
    private String workOrderNo;
    private String faultNo;
    private String maintainerName;
    private String solutionText;
    private String deadlineTime;
    private String repairResult;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
