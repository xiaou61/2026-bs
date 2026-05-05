package com.smartwarehouse.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeviceMaintenance {
    private Long id;
    private String maintenanceNo;
    private String deviceNo;
    private String deviceType;
    private String faultType;
    private String planTime;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
