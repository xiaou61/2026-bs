package com.vehicleclaim.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClaimNotice {
    private Long id;
    private String warningNo;
    private String projectNo;
    private String riskLevel;
    private String triggerReason;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}




