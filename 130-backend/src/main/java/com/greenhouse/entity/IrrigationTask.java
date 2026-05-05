package com.greenhouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class IrrigationTask {
    private Long id;
    private String taskNo;
    private String greenhouseNo;
    private String taskTime;
    private BigDecimal waterAmount;
    private String executorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
