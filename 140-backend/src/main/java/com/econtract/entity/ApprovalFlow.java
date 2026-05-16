package com.econtract.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApprovalFlow {
    private Long id;
    private String flowNo;
    private String contractTitle;
    private String currentNode;
    private String approverName;
    private String approvalOpinion;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



