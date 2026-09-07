package com.diet.management.util;

import com.diet.management.enums.Enums;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 营养计算工具类
 */
public class NutritionCalculator {

    /**
     * 计算BMI
     * BMI = 体重(kg) / 身高²(m²)
     */
    public static BigDecimal calculateBMI(BigDecimal weight, BigDecimal height) {
        if (weight == null || height == null || height.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal heightInMeter = height.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        return weight.divide(heightInMeter.multiply(heightInMeter), 2, RoundingMode.HALF_UP);
    }

    /**
     * 计算基础代谢率 (Mifflin-St Jeor公式)
     * 男性: BMR = 10 × 体重(kg) + 6.25 × 身高(cm) - 5 × 年龄 + 5
     * 女性: BMR = 10 × 体重(kg) + 6.25 × 身高(cm) - 5 × 年龄 - 161
     */
    public static BigDecimal calculateBMR(BigDecimal weight, BigDecimal height, Integer age, Enums.Gender gender) {
        if (weight == null || height == null || age == null || gender == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal bmr = new BigDecimal("10").multiply(weight)
                .add(new BigDecimal("6.25").multiply(height))
                .subtract(new BigDecimal("5").multiply(new BigDecimal(age)));
        
        if (gender == Enums.Gender.MALE) {
            bmr = bmr.add(new BigDecimal("5"));
        } else {
            bmr = bmr.subtract(new BigDecimal("161"));
        }
        
        return bmr.setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 计算每日推荐热量
     * TDEE = BMR × 活动系数
     * 活动系数: 久坐1.2, 轻度活动1.375, 中度活动1.55, 重度活动1.725, 极重度1.9
     */
    public static BigDecimal calculateTDEE(BigDecimal bmr, BigDecimal activityFactor) {
        if (bmr == null || activityFactor == null) {
            return BigDecimal.ZERO;
        }
        return bmr.multiply(activityFactor).setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 计算食物营养素含量
     * 营养素含量 = 标准含量 × 食用量 / 100
     */
    public static BigDecimal calculateNutrient(BigDecimal standardValue, BigDecimal weight) {
        if (standardValue == null || weight == null) {
            return BigDecimal.ZERO;
        }
        return standardValue.multiply(weight).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    /**
     * 计算营养素达标率
     */
    public static BigDecimal calculateAchievementRate(BigDecimal actual, BigDecimal target) {
        if (target == null || target.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return actual.divide(target, 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }
}
