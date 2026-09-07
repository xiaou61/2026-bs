package com.programming.learning.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章状态枚举
 */
@Getter
@AllArgsConstructor
public enum ArticleStatus {
    DRAFT("草稿"),
    PUBLISHED("已发布"),
    DELETED("已删除");

    private final String description;
}
