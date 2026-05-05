package com.chargepile.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class EnergyMonitor {
    private Long id;
    private String monitorNo;
    private String stationName;
    private String collectTime;
    private BigDecimal powerKwh;
    private BigDecimal loadRate;
    private String abnormalType;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
