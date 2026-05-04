package com.licensecheck.entity;

    import lombok.Data;
import java.time.LocalDateTime;

    @Data
    public class RiskIssue {
        private Long id;
    private String issueNo;
    private Long repositoryId;
    private Long resultId;
    private String issueTitle;
    private String severity;
    private String issueType;
    private String ownerName;
    private String status;
    private String dueDate;
    private String description;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    }
