package com.crossborder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("cross_border_order")
public class CrossBorderOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private String storeName;
    private String customerName;
    private String destinationCountry;
    private String currencyCode;
    private BigDecimal orderAmount;
    private String orderStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
