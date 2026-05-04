package com.privacyauth.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConsentPurpose {
    private Long id;
    private String purposeName;
    private String purposeCode;
    private String businessScene;
    private String purposeText;
    private Integer validDays;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
