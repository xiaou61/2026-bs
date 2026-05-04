package com.licensecheck.entity;

    import lombok.Data;
import java.time.LocalDateTime;

    @Data
    public class ScanTask {
        private Long id;
    private String taskNo;
    private Long repositoryId;
    private String branchName;
    private String triggerType;
    private String scanMode;
    private String status;
    private String startedAt;
    private String finishedAt;
    private String summary;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    }
