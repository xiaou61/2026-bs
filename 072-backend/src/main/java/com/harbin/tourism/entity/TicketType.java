package com.harbin.tourism.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("ticket_type")
public class TicketType {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long spotId;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Integer maxPerOrder;
    private String description;
    private Integer status;
}
