package com.privacyauth.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AccessLog {
    private Long id;
    private String logNo;
    private String operatorName;
    private String subjectName;
    private String itemName;
    private String accessResult;
    private LocalDateTime accessTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
