package com.greenhouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HarvestRecord {
    private Long id;
    private String harvestNo;
    private String batchNo;
    private String harvestDate;
    private BigDecimal harvestWeight;
    private String qualityLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
