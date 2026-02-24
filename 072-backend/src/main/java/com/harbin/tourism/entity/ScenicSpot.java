package com.harbin.tourism.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("scenic_spot")
public class ScenicSpot {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String detail;
    private String location;
    private String category;
    private String coverImg;
    private String images;
    private String openTime;
    private String closeTime;
    private BigDecimal ticketPrice;
    private BigDecimal rating;
    private Integer viewCount;
    private Integer status;
    private LocalDateTime createdAt;
}
