package com.twinpark.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EnergyMonitor {
    private Long id;
    private String energyNo;
    private String buildingName;
    private String deviceName;
    private String energyType;
    private String periodName;
    private BigDecimal energyValue;
    private BigDecimal changeRate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
