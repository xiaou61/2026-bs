package com.droneinspect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("inspection_zone")
public class InspectionZone {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String zoneNo;
    private String zoneName;
    private String cityName;
    private String riskLevel;
    private String managerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
