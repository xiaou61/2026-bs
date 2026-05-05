package com.floodcity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@TableName("water_level_point")
public class WaterLevelPoint {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String pointNo;
    private String pointName;
    private String districtName;
    private String locationName;
    private BigDecimal warningLevel;
    private String managerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
