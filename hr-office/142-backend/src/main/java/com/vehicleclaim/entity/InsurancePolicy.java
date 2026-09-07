package com.vehicleclaim.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InsurancePolicy {
    private Long id;
    private String policyNo;
    private String policyName;
    private String policyType;
    private String insurerName;
    private String coveragePeriod;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
