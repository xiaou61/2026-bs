package com.xiaou.rice.modules.booking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.rice.modules.booking.entity.BookingStatusLog;
import com.xiaou.rice.modules.booking.enums.BookingStatus;
import com.xiaou.rice.modules.booking.mapper.BookingStatusLogMapper;
import com.xiaou.rice.modules.booking.service.BookingStatusLogService;
import org.springframework.stereotype.Service;

@Service
public class BookingStatusLogServiceImpl extends ServiceImpl<BookingStatusLogMapper, BookingStatusLog> implements BookingStatusLogService {
    @Override
    public void log(Long bookingId, BookingStatus status, String note) {
        BookingStatusLog log = new BookingStatusLog();
        log.setBookingId(bookingId);
        log.setStatus(status);
        log.setNote(note);
        this.save(log);
    }
}
