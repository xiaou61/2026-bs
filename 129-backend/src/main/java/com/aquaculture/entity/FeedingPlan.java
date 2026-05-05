package com.aquaculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("feeding_plan")
public class FeedingPlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String planNo;
    private String pondNo;
    private String fishSpecies;
    private String feedType;
    private BigDecimal dailyAmount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
