package com.cloudmonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("metric_definition")
public class MetricDefinition {
    @TableId(type = IdType.AUTO)
private Long id;
private String metricName;
private String metricCode;
private String unitName;
private Integer collectInterval;
private String description;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
