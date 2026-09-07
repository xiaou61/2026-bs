package com.aquaculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("sensor_device")
public class SensorDevice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String sensorNo;
    private String pondNo;
    private String sensorType;
    private String installPosition;
    private BigDecimal batteryLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
