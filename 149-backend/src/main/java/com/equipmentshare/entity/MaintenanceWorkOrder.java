package com.equipmentshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("maintenance_work_order")
public class MaintenanceWorkOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String workOrderNo;
    private String equipmentName;
    private String faultType;
    private String reporterName;
    private String resultInfo;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








