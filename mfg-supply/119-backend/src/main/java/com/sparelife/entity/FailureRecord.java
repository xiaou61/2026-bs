package com.sparelife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("failure_record")
public class FailureRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String failureNo;
    private String equipmentName;
    private String failureType;
    private String impactLevel;
    private BigDecimal downtimeHour;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
