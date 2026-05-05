package com.twinpark.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TwinModel {
    private Long id;
    private String modelNo;
    private String modelName;
    private String modelType;
    private String deviceName;
    private String versionNo;
    private String syncTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
