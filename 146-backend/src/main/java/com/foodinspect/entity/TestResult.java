package com.foodinspect.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TestResult {
    private Long id;
    private String paymentNo;
    private String claimNo;
    private BigDecimal paymentAmount;
    private String paymentTime;
    private String operatorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






