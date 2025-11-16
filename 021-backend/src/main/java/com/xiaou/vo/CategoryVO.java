package com.xiaou.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryVO {

    private Long id;
    private String categoryName;
    private String icon;
    private Integer sortOrder;
    private LocalDateTime createTime;
}