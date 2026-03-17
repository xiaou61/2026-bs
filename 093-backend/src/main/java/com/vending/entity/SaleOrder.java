package com.vending.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("sale_order")
public class SaleOrder {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private Long machineId;
    private Long slotId;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalAmount;
    private BigDecimal payAmount;
    private String pickupCode;
    private String status;
    private LocalDateTime payTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String machineName;
    @TableField(exist = false)
    private String slotNo;
    @TableField(exist = false)
    private String productName;
}
