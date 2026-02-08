package com.mfg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("sensor_data")
public class SensorData {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long equipmentId;
    private BigDecimal temperature;
    private BigDecimal pressure;
    private BigDecimal vibration;
    private BigDecimal speed;
    private LocalDateTime collectTime;

    @TableField(exist = false)
    private String equipmentName;
}
