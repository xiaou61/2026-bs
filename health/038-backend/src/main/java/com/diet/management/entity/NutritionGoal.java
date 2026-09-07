package com.diet.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.diet.management.enums.Enums;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 营养目标实体
 */
@Data
@TableName("nutrition_goals")
public class NutritionGoal implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Enums.GoalType goalType;
    
    private BigDecimal targetWeight;
    
    private BigDecimal targetCalorie;
    
    private BigDecimal targetProtein;
    
    private BigDecimal targetFat;
    
    private BigDecimal targetCarbohydrate;
    
    private BigDecimal targetFiber;
    
    private BigDecimal targetSodium;
    
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private Integer isActive;
    
    @TableLogic
    private Integer deleted;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
