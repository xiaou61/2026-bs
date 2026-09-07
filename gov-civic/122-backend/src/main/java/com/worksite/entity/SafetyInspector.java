package com.worksite.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SafetyInspector {
    private Long id;
    private String inspectorNo;
    private String inspectorName;
    private String certificateNo;
    private String teamName;
    private String phone;
    private String specialty;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
