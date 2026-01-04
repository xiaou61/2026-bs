package com.programming.learning.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举
 */
@Getter
@AllArgsConstructor
public enum UserStatus {
    NORMAL("正常"),
    DISABLED("禁用");

    private final String description;
}
