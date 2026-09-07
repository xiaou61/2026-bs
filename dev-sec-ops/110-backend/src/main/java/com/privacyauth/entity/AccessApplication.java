package com.privacyauth.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AccessApplication {
    private Long id;
    private String applicationNo;
    private String applicantName;
    private String subjectName;
    private String purposeName;
    private String reasonText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
