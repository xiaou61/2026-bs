package com.chargepile.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class ElectricityPrice {
    private Long id;
    private String priceNo;
    private String stationName;
    private String periodType;
    private BigDecimal electricPrice;
    private BigDecimal serviceFee;
    private String effectiveDate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
