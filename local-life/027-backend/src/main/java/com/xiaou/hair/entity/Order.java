package com.xiaou.hair.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@Data
@TableName("orders")
public class Order {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String orderNo;
    
    private Long appointmentId;
    
    private Long userId;
    
    private Long storeId;
    
    private Long hairdresserId;
    
    private String serviceName;
    
    private BigDecimal originalPrice;
    
    private BigDecimal discount;
    
    private BigDecimal actualPrice;
    
    private String payType;
    
    private Integer payStatus;
    
    private LocalDateTime payTime;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
