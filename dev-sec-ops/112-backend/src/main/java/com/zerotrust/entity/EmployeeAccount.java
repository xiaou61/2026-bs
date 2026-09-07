package com.zerotrust.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmployeeAccount {
    private Long id;
    private String employeeName;
    private String employeeNo;
    private String accountName;
    private String departmentName;
    private String mfaStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
