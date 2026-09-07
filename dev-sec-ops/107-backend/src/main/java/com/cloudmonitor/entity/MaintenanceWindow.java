package com.cloudmonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("maintenance_window")
public class MaintenanceWindow {
    @TableId(type = IdType.AUTO)
private Long id;
private String windowName;
private Long serverId;
private String startTime;
private String endTime;
private String ownerName;
private String reason;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
