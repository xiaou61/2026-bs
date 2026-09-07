package com.xiaou.sport;

import com.xiaou.sport.utils.HealthMetricUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class HealthMetricUtilTest {

    @Test
    public void shouldCalculateBmiFromWeightAndHeight() {
        BigDecimal bmi = HealthMetricUtil.calculateBmi(new BigDecimal("70"), new BigDecimal("175"));
        Assertions.assertEquals(new BigDecimal("22.86"), bmi);
    }

    @Test
    public void shouldReturnNullWhenHeightIsMissing() {
        Assertions.assertNull(HealthMetricUtil.calculateBmi(new BigDecimal("70"), null));
    }
}
