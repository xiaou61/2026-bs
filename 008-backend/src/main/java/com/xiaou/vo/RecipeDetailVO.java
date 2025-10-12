package com.xiaou.vo;

import com.xiaou.dto.RecipeIngredientDTO;
import com.xiaou.dto.RecipeStepDTO;
import com.xiaou.entity.Recipe;
import lombok.Data;

import java.util.List;

@Data
public class RecipeDetailVO extends Recipe {
    private List<RecipeIngredientDTO> ingredients;
    private List<RecipeStepDTO> steps;
    private Boolean isCollected;
}

