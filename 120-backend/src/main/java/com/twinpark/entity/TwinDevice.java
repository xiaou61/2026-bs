package com.twinpark.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TwinDevice {
    private Long id;
    private String deviceNo;
    private String deviceName;
    private String buildingName;
    private String deviceType;
    private String modelNo;
    private BigDecimal healthScore;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
