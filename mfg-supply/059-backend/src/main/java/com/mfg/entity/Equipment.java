package com.mfg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("equipment")
public class Equipment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String code;
    private String name;
    private Long categoryId;
    private String model;
    private String manufacturer;
    private LocalDate purchaseDate;
    private String location;
    private String status;
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String categoryName;
}
