package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.dto.ReservationDTO;
import com.xiaou.entity.Reservation;

public interface ReservationService extends IService<Reservation> {
    void createReservation(Long userId, ReservationDTO dto);
    Page<Reservation> getMyReservations(Long userId, int current, int size);
    Page<Reservation> getShopReservations(Long shopId, int current, int size, Integer status);
}
