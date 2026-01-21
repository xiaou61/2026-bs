package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.GuideBookingDTO;
import com.xiaou.entity.GuideBooking;

public interface GuideBookingService {
    void create(Long userId, GuideBookingDTO dto);
    Page<GuideBooking> pageByUser(Long userId, int current, int size);
    Page<GuideBooking> pageByGuide(Long guideId, int current, int size);
    Page<GuideBooking> pageAll(int current, int size, Integer status);
    void cancel(Long id);
    void confirm(Long id);
    void complete(Long id);
}
