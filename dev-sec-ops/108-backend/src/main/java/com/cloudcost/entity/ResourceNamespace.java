package com.cloudcost.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResourceNamespace {
    private Long id;
    private String namespaceName;
    private String namespaceCode;
    private String accountName;
    private String businessLine;
    private String environmentName;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
