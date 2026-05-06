package com.econtract.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SignerProfile {
    private Long id;
    private String budgetNo;
    private String projectNo;
    private String categoryName;
    private BigDecimal budgetAmount;
    private BigDecimal usedAmount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



