package com.homeenergy.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class SmartMeter {
    private Long id;
    private String meterNo;
    private String homeNo;
    private String installLocation;
    private String communicationType;
    private BigDecimal ratioValue;
    private String lastHeartbeat;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
