package com.licensecheck.entity;

    import lombok.Data;
import java.time.LocalDateTime;

    @Data
    public class OrganizationTeam {
        private Long id;
    private String teamName;
    private String leaderName;
    private String contactPhone;
    private String description;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    }
