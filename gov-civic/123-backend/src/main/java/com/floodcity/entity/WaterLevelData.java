package com.floodcity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@TableName("water_level_data")
public class WaterLevelData {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String dataNo;
    private String pointName;
    private String collectTime;
    private BigDecimal waterLevel;
    private BigDecimal riseSpeed;
    private String sourceType;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
