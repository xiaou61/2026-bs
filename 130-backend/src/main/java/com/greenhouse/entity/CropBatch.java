package com.greenhouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CropBatch {
    private Long id;
    private String batchNo;
    private String greenhouseNo;
    private String cropName;
    private String plantDate;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
