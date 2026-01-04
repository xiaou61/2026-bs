package com.diet.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 食物实体
 */
@Data
@TableName("foods")
public class Food implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private String category;
    
    private String unit;
    
    private BigDecimal calorie;
    
    private BigDecimal protein;
    
    private BigDecimal fat;
    
    private BigDecimal carbohydrate;
    
    private BigDecimal fiber;
    
    private BigDecimal sodium;
    
    private String image;
    
    private String description;
    
    private Integer isCustom;
    
    private Long userId;
    
    @TableLogic
    private Integer deleted;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
