package com.petcafe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("consume_order")
public class ConsumeOrder {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private Long shopId;
    private Long menuId;
    private Integer quantity;
    private String dineType;
    private BigDecimal totalAmount;
    private BigDecimal payAmount;
    private String status;
    private String remark;
    private LocalDateTime payTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String shopName;
    @TableField(exist = false)
    private String menuName;
}
