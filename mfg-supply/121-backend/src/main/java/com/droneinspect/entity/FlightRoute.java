package com.droneinspect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@TableName("flight_route")
public class FlightRoute {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String routeNo;
    private String routeName;
    private String zoneName;
    private BigDecimal distanceKm;
    private Integer waypointCount;
    private String riskLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
