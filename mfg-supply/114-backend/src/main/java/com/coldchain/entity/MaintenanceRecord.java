package com.coldchain.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MaintenanceRecord {
    private Long id;
    private String maintenanceNo;
    private String deviceNo;
    private String maintainerName;
    private String maintenanceType;
    private String resultStatus;
    private String detailText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
