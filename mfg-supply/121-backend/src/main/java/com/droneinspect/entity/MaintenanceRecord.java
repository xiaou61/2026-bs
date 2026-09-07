package com.droneinspect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@TableName("maintenance_record")
public class MaintenanceRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String maintenanceNo;
    private String droneName;
    private String maintenanceType;
    private String technicianName;
    private String maintenanceTime;
    private BigDecimal costAmount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
