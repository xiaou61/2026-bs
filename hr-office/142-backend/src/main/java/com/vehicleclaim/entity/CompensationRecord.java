package com.vehicleclaim.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CompensationRecord {
    private Long id;
    private String compensationNo;
    private String reportNo;
    private BigDecimal compensationAmount;
    private String compensationTime;
    private String payeeName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
