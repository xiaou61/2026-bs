package com.harbin.tourism.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("route_spot")
public class RouteSpot {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long routeId;
    private Long spotId;
    private Integer orderNum;
    private BigDecimal stayHours;
}
