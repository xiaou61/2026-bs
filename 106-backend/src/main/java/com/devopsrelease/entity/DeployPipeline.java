package com.devopsrelease.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeployPipeline {
    private Long id;
private Long serviceId;
private String pipelineName;
private String branchName;
private String buildCommand;
private String imageRepo;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
