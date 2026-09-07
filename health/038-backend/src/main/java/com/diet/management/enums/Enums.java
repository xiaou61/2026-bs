package com.diet.management.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 枚举类集合
 */
public class Enums {

    /**
     * 性别枚举
     */
    @Getter
    public enum Gender {
        FEMALE(0, "女"),
        MALE(1, "男");

        @EnumValue
        private final Integer code;
        @JsonValue
        private final String desc;

        Gender(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 用户角色枚举
     */
    @Getter
    public enum UserRole {
        USER("USER", "普通用户"),
        ADMIN("ADMIN", "管理员");

        @EnumValue
        private final String code;
        @JsonValue
        private final String desc;

        UserRole(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 餐次类型枚举
     */
    @Getter
    public enum MealType {
        BREAKFAST("BREAKFAST", "早餐"),
        LUNCH("LUNCH", "午餐"),
        DINNER("DINNER", "晚餐"),
        SNACK("SNACK", "加餐");

        @EnumValue
        private final String code;
        @JsonValue
        private final String desc;

        MealType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 目标类型枚举
     */
    @Getter
    public enum GoalType {
        WEIGHT_LOSS("WEIGHT_LOSS", "减重"),
        WEIGHT_GAIN("WEIGHT_GAIN", "增重"),
        MAINTAIN("MAINTAIN", "维持"),
        MUSCLE_GAIN("MUSCLE_GAIN", "增肌");

        @EnumValue
        private final String code;
        @JsonValue
        private final String desc;

        GoalType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 食谱难度枚举
     */
    @Getter
    public enum Difficulty {
        EASY("EASY", "简单"),
        MEDIUM("MEDIUM", "中等"),
        HARD("HARD", "困难");

        @EnumValue
        private final String code;
        @JsonValue
        private final String desc;

        Difficulty(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
