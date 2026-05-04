package com.zerotrust.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AccessApplication {
    private Long id;
    private String applicationNo;
    private String deviceName;
    private String employeeName;
    private String resourceName;
    private String reasonText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
