package com.econtract.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExpirationReminder {
    private Long id;
    private String patentNo;
    private String projectNo;
    private String patentName;
    private String applicantName;
    private String grantTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



