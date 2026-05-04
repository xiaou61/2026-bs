package com.cloudmonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("alert_rule")
public class AlertRule {
    @TableId(type = IdType.AUTO)
private Long id;
private String ruleName;
private String metricCode;
private String operatorType;
private String thresholdValue;
private Integer durationMinute;
private String severity;
private String notifyGroup;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
