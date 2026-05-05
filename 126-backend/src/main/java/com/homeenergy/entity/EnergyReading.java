package com.homeenergy.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class EnergyReading {
    private Long id;
    private String readingNo;
    private String meterNo;
    private String collectTime;
    private BigDecimal totalKwh;
    private BigDecimal peakKwh;
    private BigDecimal valleyKwh;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
