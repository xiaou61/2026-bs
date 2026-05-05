package com.esgreport.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CompanySubmission {
    private Long id;
    private String submissionNo;
    private String companyName;
    private String periodMonth;
    private String fillerName;
    private String submitTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
