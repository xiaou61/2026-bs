package com.sparelife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("maintenance_plan")
public class MaintenancePlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String planNo;
    private String equipmentName;
    private String partName;
    private String suggestAction;
    private String planTime;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
