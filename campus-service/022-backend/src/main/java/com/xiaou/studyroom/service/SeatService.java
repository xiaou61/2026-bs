package com.xiaou.studyroom.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.studyroom.entity.Seat;

import java.util.List;

public interface SeatService extends IService<Seat> {

    List<Seat> getAvailableSeats(Long roomId);

    List<Seat> getSeatsByRoomId(Long roomId);

    Page<Seat> getSeatPage(int current, int size, Long roomId, Integer seatType, Integer status);

    boolean updateSeatStatus(Long seatId, Integer status);

    boolean batchUpdateSeatStatus(List<Long> seatIds, Integer status);

    int getAvailableSeatCount(Long roomId);

    List<Seat> getSeatsWithStatus(Long roomId);
}