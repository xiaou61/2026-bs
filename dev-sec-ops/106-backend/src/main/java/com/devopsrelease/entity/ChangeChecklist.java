package com.devopsrelease.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChangeChecklist {
    private Long id;
private Long orderId;
private String itemName;
private String checkOwner;
private Integer requiredFlag;
private String checkResult;
private String evidenceUrl;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
