package com.homeenergy.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class ElectricityBill {
    private Long id;
    private String billNo;
    private String homeNo;
    private String billMonth;
    private BigDecimal energyKwh;
    private BigDecimal billAmount;
    private String payMethod;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
