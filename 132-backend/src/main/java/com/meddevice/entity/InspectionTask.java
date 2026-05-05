package com.meddevice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InspectionTask {
    private Long id;
    private String inspectionNo;
    private String deviceNo;
    private String inspectionDate;
    private String inspectorName;
    private String resultText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
