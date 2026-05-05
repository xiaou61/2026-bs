package com.aquaculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("water_quality_reading")
public class WaterQualityReading {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String readingNo;
    private String pondNo;
    private String collectTime;
    private BigDecimal dissolvedOxygen;
    private BigDecimal phValue;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
