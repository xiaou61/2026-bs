package com.meddevice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SterilizationRecord {
    private Long id;
    private String recordNo;
    private String batchNo;
    private String deviceNo;
    private String sterilizeResult;
    private String operatorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
