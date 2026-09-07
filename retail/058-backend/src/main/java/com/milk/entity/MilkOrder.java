package com.milk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("milk_order")
public class MilkOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private Long subscriptionId;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String address;
    private String contactName;
    private String contactPhone;
    private LocalDate deliveryDate;
    private String deliveryTime;
    private Integer status;
    private LocalDateTime createTime;
}
