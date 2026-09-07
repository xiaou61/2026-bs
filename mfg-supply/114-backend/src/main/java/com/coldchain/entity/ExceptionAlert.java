package com.coldchain.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExceptionAlert {
    private Long id;
    private String alertNo;
    private String orderNo;
    private String alertType;
    private String cargoName;
    private String severityLevel;
    private String alertTime;
    private String detailText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
