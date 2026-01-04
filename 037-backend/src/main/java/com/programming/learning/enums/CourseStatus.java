package com.programming.learning.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 课程状态枚举
 */
@Getter
@AllArgsConstructor
public enum CourseStatus {
    DRAFT("草稿"),
    PUBLISHED("已发布"),
    OFFLINE("已下线");

    private final String description;
}
