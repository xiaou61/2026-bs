package com.cloudmonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("capacity_plan")
public class CapacityPlan {
    @TableId(type = IdType.AUTO)
private Long id;
private Long serverId;
private String planName;
private String cpuUsage;
private String memoryUsage;
private String diskUsage;
private String suggestion;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
