package com.xiaou.sport;

import com.xiaou.sport.entity.SportRecord;
import com.xiaou.sport.utils.PointRuleUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PointRuleUtilTest {

    @Test
    public void shouldCalculateRunningPoints() {
        SportRecord record = new SportRecord();
        record.setSportType("running");
        record.setDistance(new BigDecimal("5.2"));
        record.setDuration(35);

        Assertions.assertEquals(55, PointRuleUtil.calculateSportPoints(record));
    }

    @Test
    public void shouldCalculateGymPoints() {
        SportRecord record = new SportRecord();
        record.setSportType("gym");
        record.setDuration(95);

        Assertions.assertEquals(30, PointRuleUtil.calculateSportPoints(record));
    }

    @Test
    public void shouldCalculateBallGamePoints() {
        SportRecord record = new SportRecord();
        record.setSportType("basketball");
        record.setDuration(130);

        Assertions.assertEquals(30, PointRuleUtil.calculateSportPoints(record));
    }

    @Test
    public void shouldCalculatePlanProgressPointsByDelta() {
        Assertions.assertEquals(9, PointRuleUtil.calculatePlanProgressPoints(2, 5));
        Assertions.assertEquals(0, PointRuleUtil.calculatePlanProgressPoints(5, 3));
    }
}
