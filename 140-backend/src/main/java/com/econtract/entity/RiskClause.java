package com.econtract.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RiskClause {
    private Long id;
    private String clauseNo;
    private String contractTitle;
    private String clauseType;
    private String riskLevel;
    private String reviewerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



