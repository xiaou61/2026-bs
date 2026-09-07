package com.classic.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IngredientVO {
    private Long id;
    private String name;
    private Long categoryId;
    private String categoryName;
    private String natureTaste;
    private String meridian;
    private String efficacy;
    private String suitablePeople;
    private String tabooPeople;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
