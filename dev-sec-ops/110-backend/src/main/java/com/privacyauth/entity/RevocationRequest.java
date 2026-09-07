package com.privacyauth.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RevocationRequest {
    private Long id;
    private String requestNo;
    private String subjectName;
    private String authNo;
    private String reasonText;
    private LocalDateTime requestTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
