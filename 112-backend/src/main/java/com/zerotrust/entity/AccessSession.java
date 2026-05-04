package com.zerotrust.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AccessSession {
    private Long id;
    private String sessionNo;
    private String deviceName;
    private String employeeName;
    private String resourceName;
    private String sourceIp;
    private LocalDateTime loginTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
