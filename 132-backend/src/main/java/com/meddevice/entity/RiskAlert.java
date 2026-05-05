package com.meddevice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RiskAlert {
    private Long id;
    private String alertNo;
    private String deviceNo;
    private String alertLevel;
    private String triggerReason;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
