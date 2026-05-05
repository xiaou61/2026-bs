package com.aquaculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("fish_batch")
public class FishBatch {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String batchNo;
    private String pondNo;
    private String speciesName;
    private Integer seedCount;
    private String stockDate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
