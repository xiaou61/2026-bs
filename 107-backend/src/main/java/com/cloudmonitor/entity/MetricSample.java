package com.cloudmonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("metric_sample")
public class MetricSample {
    @TableId(type = IdType.AUTO)
private Long id;
private Long serverId;
private String metricCode;
private String metricValue;
private String sampleTime;
private String healthLevel;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
