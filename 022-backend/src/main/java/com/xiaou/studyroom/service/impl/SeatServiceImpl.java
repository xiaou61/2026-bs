package com.xiaou.studyroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.studyroom.entity.Reservation;
import com.xiaou.studyroom.entity.Seat;
import com.xiaou.studyroom.mapper.ReservationMapper;
import com.xiaou.studyroom.mapper.SeatMapper;
import com.xiaou.studyroom.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat> implements SeatService {

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public List<Seat> getAvailableSeats(Long roomId) {
        QueryWrapper<Seat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id", roomId)
                   .eq("seat_status", 1);
        return list(queryWrapper);
    }

    @Override
    public List<Seat> getSeatsByRoomId(Long roomId) {
        QueryWrapper<Seat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id", roomId);
        return list(queryWrapper);
    }

    @Override
    public Page<Seat> getSeatPage(int current, int size, Long roomId, Integer seatType, Integer status) {
        Page<Seat> page = new Page<>(current, size);
        QueryWrapper<Seat> queryWrapper = new QueryWrapper<>();

        if (roomId != null) {
            queryWrapper.eq("room_id", roomId);
        }
        if (seatType != null) {
            queryWrapper.eq("seat_type", seatType);
        }
        if (status != null) {
            queryWrapper.eq("seat_status", status);
        }

        return page(page, queryWrapper);
    }

    @Override
    public boolean updateSeatStatus(Long seatId, Integer status) {
        Seat seat = new Seat();
        seat.setId(seatId);
        seat.setSeatStatus(status);
        return updateById(seat);
    }

    @Override
    public boolean batchUpdateSeatStatus(List<Long> seatIds, Integer status) {
        if (seatIds == null || seatIds.isEmpty()) {
            return false;
        }

        for (Long seatId : seatIds) {
            updateSeatStatus(seatId, status);
        }
        return true;
    }

    @Override
    public int getAvailableSeatCount(Long roomId) {
        QueryWrapper<Seat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id", roomId)
                   .eq("seat_status", 1);
        return Math.toIntExact(count(queryWrapper));
    }

    @Override
    public List<Seat> getSeatsWithStatus(Long roomId) {
        QueryWrapper<Seat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id", roomId);
        List<Seat> seats = list(queryWrapper);

        // 为每个座位添加实时状态信息
        LocalDateTime now = LocalDateTime.now();
        for (Seat seat : seats) {
            // 检查是否有当前有效的预约
            QueryWrapper<Reservation> reservationQuery = new QueryWrapper<>();
            reservationQuery.eq("seat_id", seat.getId())
                          .eq("status", 2) // 已签到状态
                          .le("start_time", now)
                          .ge("end_time", now);

            long activeReservationCount = reservationMapper.selectCount(reservationQuery);
            if (activeReservationCount > 0) {
                seat.setSeatStatus(2); // 设置为占用状态
            }
        }

        return seats;
    }
}