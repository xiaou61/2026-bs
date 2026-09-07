package com.privacyauth.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConsentPolicy {
    private Long id;
    private String policyName;
    private String policyCode;
    private String purposeName;
    private String dataScope;
    private String policyVersion;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
