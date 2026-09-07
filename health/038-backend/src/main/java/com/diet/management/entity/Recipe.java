package com.diet.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.diet.management.enums.Enums;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 食谱实体
 */
@Data
@TableName("recipes")
public class Recipe implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private String category;
    
    private Enums.Difficulty difficulty;
    
    private Integer cookingTime;
    
    private Integer servings;
    
    private BigDecimal totalCalorie;
    
    private String image;
    
    private String description;
    
    private String ingredients;
    
    private String steps;
    
    private String tags;
    
    private Long authorId;
    
    private Integer viewCount;
    
    private Integer favoriteCount;
    
    @TableLogic
    private Integer deleted;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
