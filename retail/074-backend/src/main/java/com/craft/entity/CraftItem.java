package com.craft.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("craft_item")
public class CraftItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long categoryId;
    private Long sellerId;
    private String title;
    private String craftName;
    private String workshopName;
    private String craftType;
    private BigDecimal price;
    private Integer stock;
    private String cover;
    private String description;
    private String deliveryType;
    private Integer viewCount;
    private Integer soldCount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

