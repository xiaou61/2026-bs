package com.classic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("meal_plan")
public class MealPlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long formulaId;
    private String ingredientSummary;
    private String suitableConstitution;
    private String mealTime;
    private String steps;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
