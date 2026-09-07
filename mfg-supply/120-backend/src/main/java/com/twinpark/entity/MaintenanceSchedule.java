package com.twinpark.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MaintenanceSchedule {
    private Long id;
    private String scheduleNo;
    private String deviceName;
    private String maintenanceType;
    private String planTime;
    private String ownerName;
    private String executeResult;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
