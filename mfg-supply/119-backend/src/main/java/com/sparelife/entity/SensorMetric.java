package com.sparelife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("sensor_metric")
public class SensorMetric {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String metricNo;
    private String equipmentName;
    private String metricSource;
    private BigDecimal temperatureValue;
    private BigDecimal vibrationValue;
    private BigDecimal currentValue;
    private String collectTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
