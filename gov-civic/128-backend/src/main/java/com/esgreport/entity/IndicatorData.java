package com.esgreport.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class IndicatorData {
    private Long id;
    private String dataNo;
    private String indicatorName;
    private String companyName;
    private BigDecimal dataValue;
    private String unitName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
