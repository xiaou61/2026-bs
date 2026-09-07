package com.greenhouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DiseaseDiagnosis {
    private Long id;
    private String diagnosisNo;
    private String batchNo;
    private String diseaseName;
    private String suggestionText;
    private String expertName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
