package com.chargepile.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class RevenueStatistic {
    private Long id;
    private String statNo;
    private String stationName;
    private String statDate;
    private Integer orderCount;
    private BigDecimal energyKwh;
    private BigDecimal revenueAmount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
