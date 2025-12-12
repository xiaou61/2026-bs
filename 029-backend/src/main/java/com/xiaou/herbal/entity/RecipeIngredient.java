package com.xiaou.herbal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("recipe_ingredient")
public class RecipeIngredient implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long recipeId;

    private Long ingredientId;

    private BigDecimal quantity;

    private String unit;

    private LocalDateTime createTime;
}
