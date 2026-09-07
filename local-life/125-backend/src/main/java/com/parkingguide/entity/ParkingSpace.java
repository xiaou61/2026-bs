package com.parkingguide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("parking_space")
public class ParkingSpace {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String spaceNo;
    private String areaName;
    private String spaceType;
    private String plateLabel;
    private String feeType;
    private String sensorNo;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
