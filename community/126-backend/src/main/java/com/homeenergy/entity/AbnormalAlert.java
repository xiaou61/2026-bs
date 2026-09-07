package com.homeenergy.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AbnormalAlert {
    private Long id;
    private String alertNo;
    private String homeNo;
    private String deviceName;
    private String alertLevel;
    private String alertReason;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
