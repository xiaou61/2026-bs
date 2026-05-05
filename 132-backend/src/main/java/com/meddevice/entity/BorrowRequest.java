package com.meddevice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BorrowRequest {
    private Long id;
    private String requestNo;
    private String deviceNo;
    private String deptName;
    private String applicantName;
    private String requestTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
