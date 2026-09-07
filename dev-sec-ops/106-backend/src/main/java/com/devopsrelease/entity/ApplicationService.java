package com.devopsrelease.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApplicationService {
    private Long id;
private String serviceName;
private String serviceCode;
private String repoUrl;
private String language;
private String ownerName;
private String deployType;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
