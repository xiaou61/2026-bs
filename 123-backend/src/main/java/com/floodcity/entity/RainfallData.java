package com.floodcity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@TableName("rainfall_data")
public class RainfallData {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String dataNo;
    private String stationName;
    private String collectTime;
    private BigDecimal hourRainfall;
    private BigDecimal totalRainfall;
    private String rainLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
