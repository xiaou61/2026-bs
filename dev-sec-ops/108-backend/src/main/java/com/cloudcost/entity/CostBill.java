package com.cloudcost.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CostBill {
    private Long id;
    private String billNo;
    private String accountName;
    private String billMonth;
    private String currency;
    private BigDecimal totalAmount;
    private BigDecimal payAmount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
