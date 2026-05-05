package com.greenhouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class GreenhouseProfile {
    private Long id;
    private String greenhouseNo;
    private String greenhouseName;
    private String baseName;
    private BigDecimal areaSize;
    private String managerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
