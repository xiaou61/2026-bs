package com.zerotrust.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeviceCertificate {
    private Long id;
    private String certNo;
    private String deviceName;
    private String issuerName;
    private LocalDateTime issuedTime;
    private LocalDateTime expireTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
