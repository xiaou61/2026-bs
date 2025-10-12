package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("recipe_step")
public class RecipeStep {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long recipeId;
    private Integer stepNumber;
    private String description;
    private String image;
    private Integer duration;
}

