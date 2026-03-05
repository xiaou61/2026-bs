package com.repair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("technician")
public class Technician {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String name;
    private String phone;
    private String skillTags;
    private String serviceArea;
    private String level;
    private Integer workStatus;
    private BigDecimal rating;
    private Integer orderCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
