package com.licensecheck.entity;

    import lombok.Data;
import java.time.LocalDateTime;

    @Data
    public class ComplianceBaseline {
        private Long id;
    private String baselineName;
    private Long repositoryId;
    private String licenseScope;
    private String requiredLevel;
    private String effectiveDate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    }
