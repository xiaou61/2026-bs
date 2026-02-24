package com.harbin.tourism.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("route")
public class Route {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String description;
    private Integer days;
    private String difficulty;
    private String category;
    private String coverImg;
    private BigDecimal estimatedCost;
    private Integer likeCount;
    private Integer status;
    private Long userId;
    private LocalDateTime createdAt;
}
