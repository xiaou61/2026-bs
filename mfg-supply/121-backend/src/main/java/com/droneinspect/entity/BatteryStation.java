package com.droneinspect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("battery_station")
public class BatteryStation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String stationNo;
    private String stationName;
    private String zoneName;
    private Integer portCount;
    private Integer availablePort;
    private String managerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
