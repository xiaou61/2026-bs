package com.esgreport.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ImprovementTask {
    private Long id;
    private String taskNo;
    private String companyName;
    private String departmentName;
    private String improveItem;
    private String deadlineTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
