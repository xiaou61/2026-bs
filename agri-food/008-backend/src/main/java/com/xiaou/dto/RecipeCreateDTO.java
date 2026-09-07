package com.xiaou.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RecipeCreateDTO {
    private String name;
    private String image;
    private String cuisineType;
    private Integer difficulty;
    private Integer cookingTime;
    private Integer servingSize;
    private String description;
    private List<RecipeIngredientDTO> ingredients;
    private List<RecipeStepDTO> steps;
}

