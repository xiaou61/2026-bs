package com.classic.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MealPlanVO {
    private Long id;
    private String name;
    private Long formulaId;
    private String formulaName;
    private String ingredientSummary;
    private String suitableConstitution;
    private String mealTime;
    private String steps;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
