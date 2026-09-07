package com.crossborder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("reconciliation_task")
public class ReconciliationTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskNo;
    private String billNo;
    private Integer orderCount;
    private BigDecimal differenceAmount;
    private String checkResult;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
