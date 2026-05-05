package com.drugreaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("department_info")
public class DepartmentInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String deptNo;
    private String deptName;
    private String hospitalName;
    private String contactName;
    private String phoneNumber;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
