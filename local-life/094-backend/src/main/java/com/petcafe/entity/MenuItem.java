package com.petcafe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("menu_item")
public class MenuItem {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String menuNo;
    private String name;
    private Long categoryId;
    private String taste;
    private String cover;
    private BigDecimal price;
    private Integer stock;
    private Integer recommendFlag;
    private Integer status;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String categoryName;
}
