package com.chargepile.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MaintenancePlan {
    private Long id;
    private String planNo;
    private String stationName;
    private String pileNo;
    private String maintenanceType;
    private String ownerName;
    private String planTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
