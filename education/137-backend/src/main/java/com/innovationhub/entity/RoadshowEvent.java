package com.innovationhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("purchase_order")
public class RoadshowEvent {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private String supplierName;
    private String consumableName;
    private BigDecimal orderAmount;
    private String arrivalDate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


