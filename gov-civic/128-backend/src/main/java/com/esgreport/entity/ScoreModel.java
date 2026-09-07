package com.esgreport.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ScoreModel {
    private Long id;
    private String modelNo;
    private String modelName;
    private String dimensionName;
    private String scoreMethod;
    private BigDecimal weightValue;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
