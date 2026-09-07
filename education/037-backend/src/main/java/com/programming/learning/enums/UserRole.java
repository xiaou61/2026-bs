package com.programming.learning.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户角色枚举
 */
@Getter
@AllArgsConstructor
public enum UserRole {
    USER("普通用户"),
    TEACHER("教师"),
    ADMIN("管理员");

    private final String description;
}
