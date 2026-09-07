package com.greenhouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EnvironmentSensor {
    private Long id;
    private String sensorNo;
    private String greenhouseNo;
    private String sensorType;
    private String installPosition;
    private BigDecimal batteryLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
