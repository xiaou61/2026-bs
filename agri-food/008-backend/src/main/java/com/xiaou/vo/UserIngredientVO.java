package com.xiaou.vo;

import com.xiaou.entity.UserIngredient;
import lombok.Data;

@Data
public class UserIngredientVO extends UserIngredient {
    private String ingredientName;
    private String ingredientCategory;
    private Boolean isExpiring;
}

