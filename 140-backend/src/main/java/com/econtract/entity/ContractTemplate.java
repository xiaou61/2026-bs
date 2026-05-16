package com.econtract.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractTemplate {
    private Long id;
    private String templateNo;
    private String templateName;
    private String contractType;
    private String versionNo;
    private String maintainerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



