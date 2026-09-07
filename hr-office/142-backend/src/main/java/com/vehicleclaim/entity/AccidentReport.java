package com.vehicleclaim.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AccidentReport {
    private Long id;
    private String reportNo;
    private String caseName;
    private String accidentType;
    private String reportTime;
    private String reporterName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
