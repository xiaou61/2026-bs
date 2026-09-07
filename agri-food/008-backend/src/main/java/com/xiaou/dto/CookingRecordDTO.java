package com.xiaou.dto;

import lombok.Data;

@Data
public class CookingRecordDTO {
    private Long recipeId;
    private String image;
    private String note;
    private Integer tasteRating;
    private Integer difficultyRating;
    private Integer timeRating;
}

