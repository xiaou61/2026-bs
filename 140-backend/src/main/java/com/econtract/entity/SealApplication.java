package com.econtract.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SealApplication {
    private Long id;
    private String invoiceNo;
    private String claimNo;
    private String invoiceType;
    private BigDecimal invoiceAmount;
    private String issuerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



