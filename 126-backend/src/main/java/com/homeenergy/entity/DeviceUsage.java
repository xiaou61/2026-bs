package com.homeenergy.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class DeviceUsage {
    private Long id;
    private String usageNo;
    private String deviceName;
    private String usageDate;
    private BigDecimal energyKwh;
    private BigDecimal runtimeHour;
    private BigDecimal energyRatio;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
