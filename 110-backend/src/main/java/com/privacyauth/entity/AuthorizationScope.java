package com.privacyauth.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuthorizationScope {
    private Long id;
    private String authNo;
    private String itemName;
    private String scopeType;
    private String requiredFlag;
    private String descriptionText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
