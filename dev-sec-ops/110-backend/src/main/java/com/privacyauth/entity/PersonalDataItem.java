package com.privacyauth.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PersonalDataItem {
    private Long id;
    private String itemName;
    private String itemCode;
    private String categoryName;
    private String sensitiveLevel;
    private Integer retentionDays;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
