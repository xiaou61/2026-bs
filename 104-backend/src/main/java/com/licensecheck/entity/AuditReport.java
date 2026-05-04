package com.licensecheck.entity;

    import lombok.Data;
import java.time.LocalDateTime;

    @Data
    public class AuditReport {
        private Long id;
    private String reportNo;
    private Long repositoryId;
    private Long taskId;
    private String reportTitle;
    private String riskSummary;
    private String conclusion;
    private String status;
    private String publishedAt;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    }
