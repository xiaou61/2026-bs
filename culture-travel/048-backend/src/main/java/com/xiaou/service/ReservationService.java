package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.ReservationDTO;
import com.xiaou.entity.Reservation;

public interface ReservationService {
    void create(Long userId, ReservationDTO dto);
    Page<Reservation> pageByUser(Long userId, int current, int size);
    Page<Reservation> pageAll(int current, int size, Integer status);
    void cancel(Long id);
    void confirm(Long id);
    void complete(Long id);
}
