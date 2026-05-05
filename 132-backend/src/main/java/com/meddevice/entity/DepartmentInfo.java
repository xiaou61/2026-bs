package com.meddevice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DepartmentInfo {
    private Long id;
    private String deptNo;
    private String deptName;
    private String floorName;
    private String contactName;
    private String phoneNumber;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
