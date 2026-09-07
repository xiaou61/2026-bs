package com.licensecheck.entity;

    import lombok.Data;
import java.time.LocalDateTime;

    @Data
    public class ProjectRepository {
        private Long id;
    private String repoName;
    private String repoCode;
    private String ownerTeam;
    private String gitUrl;
    private String language;
    private String riskLevel;
    private String status;
    private String lastScanTime;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    }
