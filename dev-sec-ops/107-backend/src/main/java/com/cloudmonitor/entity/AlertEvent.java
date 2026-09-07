package com.cloudmonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("alert_event")
public class AlertEvent {
    @TableId(type = IdType.AUTO)
private Long id;
private String eventNo;
private Long serverId;
private Long ruleId;
private String metricCode;
private String currentValue;
private String severity;
private String ownerName;
private String status;
private String triggeredAt;
private String resolvedAt;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
