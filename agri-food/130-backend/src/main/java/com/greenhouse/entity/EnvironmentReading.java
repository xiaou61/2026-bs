package com.greenhouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EnvironmentReading {
    private Long id;
    private String readingNo;
    private String greenhouseNo;
    private String collectTime;
    private BigDecimal temperatureValue;
    private BigDecimal humidityValue;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
