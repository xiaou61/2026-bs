package com.outpatientexam.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CheckinRecord {
    private Long id;
    private String approvalNo;
    private String claimNo;
    private String nodeName;
    private String approverName;
    private String approvalOpinion;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








