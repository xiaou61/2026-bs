package com.cloudmonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("dashboard_widget")
public class DashboardWidget {
    @TableId(type = IdType.AUTO)
private Long id;
private String widgetName;
private String widgetType;
private String metricCode;
private Integer refreshSecond;
private Integer sortNo;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
