package com.licensecheck.entity;

    import lombok.Data;
import java.time.LocalDateTime;

    @Data
    public class RepositoryBranch {
        private Long id;
    private Long repositoryId;
    private String branchName;
    private String commitHash;
    private Integer defaultFlag;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    }
