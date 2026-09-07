package com.programming.learning.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 课程难度枚举
 */
@Getter
@AllArgsConstructor
public enum CourseLevel {
    BEGINNER("入门"),
    INTERMEDIATE("进阶"),
    ADVANCED("高级");

    private final String description;
}
