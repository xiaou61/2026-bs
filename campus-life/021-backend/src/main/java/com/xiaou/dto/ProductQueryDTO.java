package com.xiaou.dto;

import lombok.Data;

@Data
public class ProductQueryDTO {

    private Long categoryId;
    private String keyword;
    private String condition;
    private String sortBy; // price, viewCount, favoriteCount
    private String sortOrder; // asc, desc
    private Integer current = 1;
    private Integer size = 10;
}