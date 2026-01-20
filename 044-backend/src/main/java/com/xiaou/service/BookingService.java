package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.dto.BookingDTO;
import com.xiaou.entity.Booking;

public interface BookingService extends IService<Booking> {
    void createBooking(Long userId, BookingDTO dto);
    void cancelBooking(Long userId, Long id, String reason);
    void confirmBooking(Long hostId, Long id);
    IPage<Booking> pageByUser(Long userId, Integer current, Integer size, Integer status);
    IPage<Booking> pageByHost(Long hostId, Integer current, Integer size, Integer status);
    Booking detail(Long userId, Long id);
}
