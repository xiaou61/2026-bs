package com.zerotrust.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AccessPolicy {
    private Long id;
    private String policyName;
    private String policyCode;
    private String resourceName;
    private String conditionText;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
