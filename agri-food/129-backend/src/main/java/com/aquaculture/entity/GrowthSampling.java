package com.aquaculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("growth_sampling")
public class GrowthSampling {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String sampleNo;
    private String batchNo;
    private String sampleDate;
    private BigDecimal avgWeight;
    private String recorderName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
