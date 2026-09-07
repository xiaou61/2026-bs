package com.parkingguide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("navigation_route")
public class NavigationRoute {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String routeNo;
    private String lotName;
    private String entryName;
    private String targetArea;
    private Integer distanceMeter;
    private String congestionLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
