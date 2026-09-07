package com.licensecheck.entity;

    import lombok.Data;
import java.time.LocalDateTime;

    @Data
    public class DependencyPackage {
        private Long id;
    private Long repositoryId;
    private String packageName;
    private String packageVersion;
    private String packageManager;
    private String licenseName;
    private String scopeName;
    private String sourceUrl;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    }
