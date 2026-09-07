package com.greenhouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PestWarning {
    private Long id;
    private String warningNo;
    private String greenhouseNo;
    private String pestType;
    private String warningLevel;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
