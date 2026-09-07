package com.eldercare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CareTeam {
    private Long id;
    private String teamNo;
    private String teamName;
    private String serviceExpertise;
    private String buildTime;
    private String serviceArea;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
