package com.xiaou.sport.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class HealthMetricUtil {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    private HealthMetricUtil() {
    }

    public static BigDecimal calculateBmi(BigDecimal weightKg, BigDecimal heightCm) {
        if (weightKg == null || heightCm == null) {
            return null;
        }
        if (weightKg.compareTo(BigDecimal.ZERO) <= 0 || heightCm.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        BigDecimal heightInMeters = heightCm.divide(ONE_HUNDRED, 4, RoundingMode.HALF_UP);
        BigDecimal divisor = heightInMeters.multiply(heightInMeters);
        if (divisor.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return weightKg.divide(divisor, 2, RoundingMode.HALF_UP);
    }
}
