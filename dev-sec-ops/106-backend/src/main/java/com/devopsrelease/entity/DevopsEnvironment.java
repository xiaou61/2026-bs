package com.devopsrelease.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DevopsEnvironment {
    private Long id;
private String envName;
private String envCode;
private String clusterName;
private String namespaceName;
private String ownerName;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
