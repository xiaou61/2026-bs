package com.eldercare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("check_item")
public class CheckItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String itemCode;
    private String itemName;
    private String unit;
    private BigDecimal lowLimit;
    private BigDecimal highLimit;
    private String warningLevel;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
