package com.zerotrust.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RiskModel {
    private Long id;
    private String modelName;
    private String modelCode;
    private String modelType;
    private Integer thresholdScore;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
