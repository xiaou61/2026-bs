package com.devopsrelease.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VersionArtifact {
    private Long id;
private Long serviceId;
private String versionNo;
private String artifactName;
private String imageTag;
private String checksum;
private String buildTime;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
