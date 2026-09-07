package com.xiaou.rice.modules.booking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.rice.modules.booking.entity.BookingStatusLog;

import com.xiaou.rice.modules.booking.enums.BookingStatus;

public interface BookingStatusLogService extends IService<BookingStatusLog> {
    void log(Long bookingId, BookingStatus status, String note);
}
