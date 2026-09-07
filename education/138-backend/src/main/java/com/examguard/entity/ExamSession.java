package com.examguard.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExamSession {
    private Long id;
    private String claimNo;
    private String projectNo;
    private String applicantName;
    private BigDecimal claimAmount;
    private String claimTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


