package com.xiaou.sport.utils;

import com.xiaou.sport.entity.SportRecord;

import java.math.BigDecimal;

public final class PointRuleUtil {

    private static final int ACTIVITY_JOIN_POINTS = 5;
    private static final int PLAN_DAY_POINTS = 3;

    private PointRuleUtil() {
    }

    public static int calculateSportPoints(SportRecord record) {
        if (record == null) {
            return 0;
        }

        int points = 0;
        String sportType = record.getSportType();
        Integer duration = record.getDuration();
        BigDecimal distance = record.getDistance();

        if ("running".equals(sportType)) {
            if (distance != null) {
                points += distance.intValue() * 10;
            }
            if (duration != null && duration >= 30) {
                points += 5;
            }
            return points;
        }

        if ("gym".equals(sportType)) {
            if (duration != null) {
                points += (duration / 30) * 10;
            }
            return points;
        }

        if (duration != null) {
            points += (duration / 60) * 15;
        }
        return points;
    }

    public static int calculatePlanProgressPoints(Integer oldCompletedDays, Integer newCompletedDays) {
        int oldValue = oldCompletedDays == null ? 0 : Math.max(oldCompletedDays, 0);
        int newValue = newCompletedDays == null ? 0 : Math.max(newCompletedDays, 0);
        return Math.max(newValue - oldValue, 0) * PLAN_DAY_POINTS;
    }

    public static int getActivityJoinPoints() {
        return ACTIVITY_JOIN_POINTS;
    }
}
