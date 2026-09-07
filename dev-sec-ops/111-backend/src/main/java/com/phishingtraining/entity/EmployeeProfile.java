package com.phishingtraining.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("employee_profile")
public class EmployeeProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String employeeName;
    private String employeeNo;
    private String email;
    private String departmentName;
    private String positionName;
    private String riskLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
