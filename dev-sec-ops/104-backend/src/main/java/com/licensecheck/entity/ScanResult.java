package com.licensecheck.entity;

    import lombok.Data;
import java.time.LocalDateTime;

    @Data
    public class ScanResult {
        private Long id;
    private Long taskId;
    private String packageName;
    private String packageVersion;
    private String licenseName;
    private String riskLevel;
    private String ruleMatched;
    private String evidence;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    }
