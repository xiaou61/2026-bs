package com.privacyauth.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DataSubject {
    private Long id;
    private String subjectName;
    private String subjectCode;
    private String identityType;
    private String phone;
    private String email;
    private String regionName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
