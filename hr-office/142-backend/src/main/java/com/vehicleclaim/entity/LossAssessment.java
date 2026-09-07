package com.vehicleclaim.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LossAssessment {
    private Long id;
    private String assessmentNo;
    private String reportNo;
    private java.math.BigDecimal assessmentAmount;
    private String assessmentTime;
    private String assessorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
