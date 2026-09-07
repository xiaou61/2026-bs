package com.worksite.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RiskWarning {
    private Long id;
    private String warningNo;
    private String riskObject;
    private String warningLevel;
    private String warningReason;
    private String handlerName;
    private String triggerTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
