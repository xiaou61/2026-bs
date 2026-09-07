package com.devopsrelease.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApprovalFlow {
    private Long id;
private String flowName;
private String businessType;
private String nodeName;
private String approverRole;
private Integer sortNo;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
