package com.aquaculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("production_statistic")
public class ProductionStatistic {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String statNo;
    private String pondNo;
    private String statMonth;
    private BigDecimal outputWeight;
    private BigDecimal survivalRate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
