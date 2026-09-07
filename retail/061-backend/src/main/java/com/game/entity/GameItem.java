package com.game.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("game_item")
public class GameItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long categoryId;
    private Long sellerId;
    private String title;
    private String gameName;
    private String serverName;
    private String itemType;
    private BigDecimal price;
    private Integer stock;
    private String cover;
    private String description;
    private String tradeMode;
    private Integer viewCount;
    private Integer soldCount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
