package com.esgreport.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class IndicatorLibrary {
    private Long id;
    private String indicatorNo;
    private String indicatorName;
    private String dimensionName;
    private String caliberText;
    private BigDecimal weightValue;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
