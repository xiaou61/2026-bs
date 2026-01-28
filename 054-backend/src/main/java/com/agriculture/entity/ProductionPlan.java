package com.agriculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("production_plan")
public class ProductionPlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String planName;
    private Long cropId;
    private BigDecimal area;
    private BigDecimal expectedYield;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
    private Integer progress;
    private Long creatorId;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
