package com.licensecheck.entity;

    import lombok.Data;
import java.time.LocalDateTime;

    @Data
    public class ApprovalRecord {
        private Long id;
    private String businessType;
    private Long businessId;
    private String applicant;
    private String approver;
    private String decision;
    private String opinion;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    }
