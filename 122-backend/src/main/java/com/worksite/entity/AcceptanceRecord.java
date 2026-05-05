package com.worksite.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class AcceptanceRecord {
    private Long id;
    private String acceptNo;
    private String orderNo;
    private String inspectorName;
    private String acceptTime;
    private String acceptResult;
    private BigDecimal score;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
