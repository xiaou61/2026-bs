package com.xiaou.rice.modules.booking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.rice.modules.booking.entity.Booking;
import com.xiaou.rice.modules.booking.mapper.BookingMapper;
import com.xiaou.rice.modules.booking.service.BookingService;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl extends ServiceImpl<BookingMapper, Booking> implements BookingService {
}
