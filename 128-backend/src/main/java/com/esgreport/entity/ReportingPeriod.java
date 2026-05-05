package com.esgreport.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReportingPeriod {
    private Long id;
    private String periodNo;
    private String companyName;
    private String reportMonth;
    private String templateName;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
