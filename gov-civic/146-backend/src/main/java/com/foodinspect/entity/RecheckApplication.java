package com.foodinspect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RecheckApplication {
    private Long id;
    private String applicationNo;
    private String foodName;
    private String recheckReason;
    private String applicationTime;
    private String applicantName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






