package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.dto.ReservationDTO;
import com.xiaou.entity.Reservation;
import com.xiaou.mapper.ReservationMapper;
import com.xiaou.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation> implements ReservationService {

    @Override
    public void createReservation(Long userId, ReservationDTO dto) {
        Reservation r = new Reservation();
        r.setOrderNo("RES" + System.currentTimeMillis());
        r.setUserId(userId);
        r.setShopId(dto.getShopId());
        r.setRoomId(dto.getRoomId());
        r.setScriptId(dto.getScriptId());
        r.setReservationDate(LocalDate.parse(dto.getReservationDate()));
        r.setTimeSlot(dto.getTimeSlot());
        r.setPlayerCount(dto.getPlayerCount());
        r.setContactName(dto.getContactName());
        r.setContactPhone(dto.getContactPhone());
        r.setTotalPrice(dto.getTotalPrice());
        r.setRemark(dto.getRemark());
        r.setStatus(0);
        save(r);
    }

    @Override
    public Page<Reservation> getMyReservations(Long userId, int current, int size) {
        return page(new Page<>(current, size),
                new LambdaQueryWrapper<Reservation>()
                        .eq(Reservation::getUserId, userId)
                        .orderByDesc(Reservation::getCreateTime));
    }

    @Override
    public Page<Reservation> getShopReservations(Long shopId, int current, int size, Integer status) {
        LambdaQueryWrapper<Reservation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Reservation::getShopId, shopId);
        if (status != null) wrapper.eq(Reservation::getStatus, status);
        wrapper.orderByDesc(Reservation::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }
}
