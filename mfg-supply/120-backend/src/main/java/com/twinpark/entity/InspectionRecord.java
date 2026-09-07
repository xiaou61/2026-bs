package com.twinpark.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InspectionRecord {
    private Long id;
    private String recordNo;
    private String taskNo;
    private String pointName;
    private String resultType;
    private BigDecimal meterValue;
    private String inspectTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
