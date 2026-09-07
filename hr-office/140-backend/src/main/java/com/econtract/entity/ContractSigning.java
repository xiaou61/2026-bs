package com.econtract.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractSigning {
    private Long id;
    private String signingNo;
    private String contractTitle;
    private String signatoryName;
    private String signTime;
    private String signingStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



