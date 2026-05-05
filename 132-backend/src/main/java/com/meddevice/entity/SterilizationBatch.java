package com.meddevice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SterilizationBatch {
    private Long id;
    private String batchNo;
    private String sterilizeMethod;
    private String machineName;
    private String ownerName;
    private String startTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
