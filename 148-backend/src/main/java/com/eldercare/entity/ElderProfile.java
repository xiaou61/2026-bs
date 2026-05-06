package com.eldercare.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ElderProfile {
    private Long id;
    private String categoryNo;
    private String categoryName;
    private String usageScope;
    private String controlMode;
    private String managerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}







