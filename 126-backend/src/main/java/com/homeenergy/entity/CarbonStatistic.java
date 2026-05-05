package com.homeenergy.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class CarbonStatistic {
    private Long id;
    private String statNo;
    private String homeNo;
    private String statMonth;
    private BigDecimal energyKwh;
    private BigDecimal carbonKg;
    private BigDecimal yearRate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
