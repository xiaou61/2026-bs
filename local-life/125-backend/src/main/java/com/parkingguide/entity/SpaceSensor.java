package com.parkingguide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("space_sensor")
public class SpaceSensor {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String sensorNo;
    private String spaceNo;
    private String deviceType;
    private Integer batteryLevel;
    private Integer signalStrength;
    private String lastHeartbeat;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
