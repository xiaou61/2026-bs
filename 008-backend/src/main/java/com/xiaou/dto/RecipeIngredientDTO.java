package com.xiaou.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecipeIngredientDTO {
    private Long ingredientId;
    private String ingredientName;
    private BigDecimal quantity;
    private String unit;
    private Integer isRequired;
}

