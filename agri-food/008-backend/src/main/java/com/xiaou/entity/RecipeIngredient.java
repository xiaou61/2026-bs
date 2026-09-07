package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("recipe_ingredient")
public class RecipeIngredient {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long recipeId;
    private Long ingredientId;
    private BigDecimal quantity;
    private String unit;
    private Integer isRequired;
}

