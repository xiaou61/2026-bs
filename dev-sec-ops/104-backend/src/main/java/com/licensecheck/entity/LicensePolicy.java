package com.licensecheck.entity;

    import lombok.Data;
import java.time.LocalDateTime;

    @Data
    public class LicensePolicy {
        private Long id;
    private String licenseName;
    private String riskLevel;
    private Integer compatibleFlag;
    private Integer commercialUseFlag;
    private String policyText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    }
