package com.xiaou.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NutritionVO {
    private BigDecimal totalCalories;
    private BigDecimal totalProtein;
    private BigDecimal totalFat;
    private BigDecimal totalCarbohydrate;
    private BigDecimal totalFiber;
}

