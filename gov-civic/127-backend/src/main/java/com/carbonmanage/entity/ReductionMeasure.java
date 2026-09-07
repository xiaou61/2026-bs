package com.carbonmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("reduction_measure")
public class ReductionMeasure {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String measureNo;
    private String taskNo;
    private String measureName;
    private BigDecimal investmentAmount;
    private BigDecimal expectedReduction;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
