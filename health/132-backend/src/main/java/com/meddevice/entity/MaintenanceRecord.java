package com.meddevice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MaintenanceRecord {
    private Long id;
    private String maintenanceNo;
    private String deviceNo;
    private String maintenanceType;
    private String maintenanceTime;
    private String maintainerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
