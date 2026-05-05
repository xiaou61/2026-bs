package com.twinpark.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SensorData {
    private Long id;
    private String dataNo;
    private String deviceName;
    private String sensorType;
    private BigDecimal temperatureValue;
    private BigDecimal energyValue;
    private BigDecimal pressureValue;
    private String collectTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
