package com.privacyauth.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuthorizationRecord {
    private Long id;
    private String authNo;
    private String subjectName;
    private String purposeName;
    private String channelName;
    private LocalDateTime grantTime;
    private LocalDateTime expireTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
