package com.econtract.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SealRecord {
    private Long id;
    private String sealRecordNo;
    private String contractTitle;
    private String sealType;
    private String operatorName;
    private String sealTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



