package com.classic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ingredient")
public class Ingredient {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long categoryId;
    private String natureTaste;
    private String meridian;
    private String efficacy;
    private String suitablePeople;
    private String tabooPeople;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
