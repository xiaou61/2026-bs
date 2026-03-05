package com.repair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("application")
public class Application {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long childId;
    private String applyReason;
    private BigDecimal requiredAmount;
    private Integer applyStatus;
    private Long reviewerId;
    private LocalDateTime reviewTime;
    private String reviewComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

