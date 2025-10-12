package com.xiaou.dto;

import lombok.Data;

@Data
public class RecipeStepDTO {
    private Integer stepNumber;
    private String description;
    private String image;
    private Integer duration;
}

