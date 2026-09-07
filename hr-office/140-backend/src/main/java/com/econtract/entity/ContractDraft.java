package com.econtract.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractDraft {
    private Long id;
    private String draftNo;
    private String templateName;
    private String contractTitle;
    private String applicantName;
    private String submitTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



