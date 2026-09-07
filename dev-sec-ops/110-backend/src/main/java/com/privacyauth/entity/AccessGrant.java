package com.privacyauth.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AccessGrant {
    private Long id;
    private String grantNo;
    private String applicationNo;
    private String granteeName;
    private String dataScope;
    private LocalDateTime expireTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
