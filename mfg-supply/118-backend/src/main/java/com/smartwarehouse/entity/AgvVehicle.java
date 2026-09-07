package com.smartwarehouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AgvVehicle {
    private Long id;
    private String agvNo;
    private String agvModel;
    private String currentZone;
    private Integer batteryLevel;
    private BigDecimal loadWeight;
    private Integer taskCount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
