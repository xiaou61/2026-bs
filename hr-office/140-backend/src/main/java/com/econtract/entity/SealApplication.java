package com.econtract.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SealApplication {
    private Long id;
    private String applicationNo;
    private String contractTitle;
    private String sealType;
    private String applicantName;
    private String useDate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



