package com.aquaculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("feeding_record")
public class FeedingRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String feedNo;
    private String pondNo;
    private String feedTime;
    private BigDecimal feedAmount;
    private String operatorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
