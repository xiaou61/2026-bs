package com.droneinspect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("drone_device")
public class DroneDevice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String droneNo;
    private String droneName;
    private String modelName;
    private Integer batteryLevel;
    private String ownerTeam;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
