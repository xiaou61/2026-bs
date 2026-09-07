package com.licensecheck.entity;

    import lombok.Data;
import java.time.LocalDateTime;

    @Data
    public class OperationLog {
        private Long id;
    private String username;
    private String action;
    private String targetType;
    private Long targetId;
    private String detail;
    private String ipAddress;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    }
