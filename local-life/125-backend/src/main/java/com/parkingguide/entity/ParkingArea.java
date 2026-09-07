package com.parkingguide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("parking_area")
public class ParkingArea {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String areaNo;
    private String lotName;
    private String areaName;
    private String floorName;
    private Integer spaceCount;
    private String managerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
