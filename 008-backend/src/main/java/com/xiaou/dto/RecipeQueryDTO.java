package com.xiaou.dto;

import lombok.Data;

@Data
public class RecipeQueryDTO {
    private String name;
    private String cuisineType;
    private Integer difficulty;
    private Integer maxCookingTime;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}

