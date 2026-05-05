package com.carbonmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("reduction_task")
public class ReductionTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskNo;
    private String companyNo;
    private String taskName;
    private BigDecimal targetAmount;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
