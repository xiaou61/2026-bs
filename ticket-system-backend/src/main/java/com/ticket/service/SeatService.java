package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ticket.common.BusinessException;
import com.ticket.entity.Seat;
import com.ticket.mapper.SeatMapper;
import com.ticket.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SeatService {

    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private RedisUtils redisUtils;

    public List<Seat> getSeatsByShowtime(Long showtimeId) {
        QueryWrapper<Seat> wrapper = new QueryWrapper<>();
        wrapper.eq("showtime_id", showtimeId)
                .orderByAsc("row_num")
                .orderByAsc("col_num");
        List<Seat> seats = seatMapper.selectList(wrapper);
        
        seats.forEach(seat -> {
            String lockKey = "seat:lock:" + seat.getId();
            if (redisUtils.hasKey(lockKey)) {
                seat.setStatus("locked");
            }
        });
        
        return seats;
    }

    public boolean lockSeats(List<Long> seatIds, Long userId) {
        for (Long seatId : seatIds) {
            Seat seat = seatMapper.selectById(seatId);
            if (seat == null || !"available".equals(seat.getStatus())) {
                return false;
            }
            
            String lockKey = "seat:lock:" + seatId;
            if (redisUtils.hasKey(lockKey)) {
                return false;
            }
            
            redisUtils.set(lockKey, userId, 15, TimeUnit.MINUTES);
        }
        return true;
    }

    public void unlockSeats(List<Long> seatIds) {
        for (Long seatId : seatIds) {
            String lockKey = "seat:lock:" + seatId;
            redisUtils.delete(lockKey);
        }
    }

    public void updateSeatStatus(Long seatId, String status) {
        Seat seat = new Seat();
        seat.setId(seatId);
        seat.setStatus(status);
        seatMapper.updateById(seat);
    }

    public List<Seat> getSeatsByIds(List<Long> seatIds) {
        return seatMapper.selectBatchIds(seatIds);
    }
}
