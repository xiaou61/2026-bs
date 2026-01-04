package com.programming.learning.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 问题状态枚举
 */
@Getter
@AllArgsConstructor
public enum QuestionStatus {
    OPEN("待解决"),
    SOLVED("已解决"),
    CLOSED("已关闭");

    private final String description;
}
