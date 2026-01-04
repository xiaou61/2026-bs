package com.diet.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.diet.management.enums.Enums;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 饮食记录实体
 */
@Data
@TableName("diet_records")
public class DietRecord implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long foodId;
    
    private String foodName;
    
    private Enums.MealType mealType;
    
    private BigDecimal weight;
    
    private BigDecimal calorie;
    
    private BigDecimal protein;
    
    private BigDecimal fat;
    
    private BigDecimal carbohydrate;
    
    private BigDecimal fiber;
    
    private BigDecimal sodium;
    
    private LocalDate eatDate;
    
    private LocalTime eatTime;
    
    private String remark;
    
    @TableLogic
    private Integer deleted;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
