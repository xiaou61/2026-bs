package com.petcafe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("cafe_shop")
public class CafeShop {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String shopNo;
    private Long areaId;
    private String name;
    private String theme;
    private String openTime;
    private String closeTime;
    private String status;
    private String cover;
    private String managerName;
    private String managerPhone;
    private Integer score;
    private java.math.BigDecimal perCapita;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String areaName;
}
