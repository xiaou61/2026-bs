package com.xiaou.herbal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCreateRequest {

    @NotBlank(message = "食谱名称不能为空")
    private String title;

    @NotBlank(message = "食谱描述不能为空")
    private String description;

    private String coverImage;

    private String efficacy;

    private String nutrition;

    @NotNull(message = "难度级别不能为空")
    private Integer difficulty;

    @NotNull(message = "烹饪时间不能为空")
    private Integer cookTime;

    private Integer servings;

    private String applicablePeople;

    private String seasons;

    private List<RecipeIngredientRequest> ingredients;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecipeIngredientRequest {
        private Long ingredientId;
        private String quantity;
        private String unit;
    }
}
