package com.xiaou.rice.modules.booking.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum BookingStatus {
    @EnumValue
    PENDING,
    @EnumValue
    CONFIRMED,
    @EnumValue
    IN_PROGRESS,
    @EnumValue
    SETTLED,
    @EnumValue
    CANCELLED
}
