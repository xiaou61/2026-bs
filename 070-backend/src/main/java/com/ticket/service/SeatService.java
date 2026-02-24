package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ticket.common.BusinessException;
import com.ticket.entity.Hall;
import com.ticket.entity.Seat;
import com.ticket.entity.Showtime;
import com.ticket.mapper.HallMapper;
import com.ticket.mapper.SeatMapper;
import com.ticket.mapper.ShowtimeMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SeatService {

    @Resource
    private SeatMapper seatMapper;

    @Resource
    private ShowtimeMapper showtimeMapper;

    @Resource
    private HallMapper hallMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ShowtimeService showtimeService;

    public List<Seat> listByShowtime(Long showtimeId) {
        Showtime showtime = showtimeMapper.selectById(showtimeId);
        if (showtime == null) {
            throw new BusinessException("场次不存在");
        }
        initSeats(showtimeId, showtime);
        return seatMapper.selectList(new LambdaQueryWrapper<Seat>()
                .eq(Seat::getShowtimeId, showtimeId)
                .orderByAsc(Seat::getRowNum, Seat::getColNum));
    }

    public List<Seat> getSeats(Long showtimeId, List<Long> seatIds) {
        if (seatIds == null || seatIds.isEmpty()) {
            throw new BusinessException("座位不能为空");
        }
        List<Seat> seats = seatMapper.selectList(new LambdaQueryWrapper<Seat>()
                .eq(Seat::getShowtimeId, showtimeId)
                .in(Seat::getId, seatIds));
        if (seats.size() != seatIds.size()) {
            throw new BusinessException("存在无效座位");
        }
        seats.sort(Comparator.comparing(Seat::getRowNum).thenComparing(Seat::getColNum));
        return seats;
    }

    @Transactional(rollbackFor = Exception.class)
    public void lock(Long userId, Long showtimeId, List<Long> seatIds) {
        List<Seat> seats = getSeats(showtimeId, seatIds);
        for (Seat seat : seats) {
            if (!"AVAILABLE".equals(seat.getStatus())) {
                if (!"LOCKED".equals(seat.getStatus()) || !isLockedByUser(userId, showtimeId, seat.getId())) {
                    throw new BusinessException("座位已不可用: " + seat.getRowNum() + "排" + seat.getColNum() + "座");
                }
            }
            String key = lockKey(showtimeId, seat.getId());
            Object locker = redisTemplate.opsForValue().get(key);
            if (locker != null && !Objects.equals(String.valueOf(locker), String.valueOf(userId))) {
                throw new BusinessException("座位已被锁定: " + seat.getRowNum() + "排" + seat.getColNum() + "座");
            }
        }
        for (Seat seat : seats) {
            redisTemplate.opsForValue().set(lockKey(showtimeId, seat.getId()), userId, 15, TimeUnit.MINUTES);
            seat.setStatus("LOCKED");
            seatMapper.updateById(seat);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void unlockByUser(Long userId, Long showtimeId, List<Long> seatIds) {
        List<Seat> seats = getSeats(showtimeId, seatIds);
        for (Seat seat : seats) {
            String key = lockKey(showtimeId, seat.getId());
            Object locker = redisTemplate.opsForValue().get(key);
            if (locker != null && Objects.equals(String.valueOf(locker), String.valueOf(userId))) {
                redisTemplate.delete(key);
                seat.setStatus("AVAILABLE");
                seatMapper.updateById(seat);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void unlockForOrder(Long showtimeId, List<Long> seatIds) {
        List<Seat> seats = getSeats(showtimeId, seatIds);
        for (Seat seat : seats) {
            redisTemplate.delete(lockKey(showtimeId, seat.getId()));
            seat.setStatus("AVAILABLE");
            seatMapper.updateById(seat);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void occupy(Long showtimeId, List<Long> seatIds) {
        List<Seat> seats = getSeats(showtimeId, seatIds);
        for (Seat seat : seats) {
            if ("SOLD".equals(seat.getStatus())) {
                throw new BusinessException("座位已售出");
            }
            redisTemplate.delete(lockKey(showtimeId, seat.getId()));
            seat.setStatus("SOLD");
            seatMapper.updateById(seat);
        }
        showtimeService.changeAvailableSeats(showtimeId, -seats.size());
    }

    @Transactional(rollbackFor = Exception.class)
    public void releaseSold(Long showtimeId, List<Long> seatIds) {
        List<Seat> seats = getSeats(showtimeId, seatIds);
        int count = 0;
        for (Seat seat : seats) {
            if ("SOLD".equals(seat.getStatus())) {
                seat.setStatus("AVAILABLE");
                seatMapper.updateById(seat);
                count++;
            }
            redisTemplate.delete(lockKey(showtimeId, seat.getId()));
        }
        if (count > 0) {
            showtimeService.changeAvailableSeats(showtimeId, count);
        }
    }

    public boolean isLockedByUser(Long userId, Long showtimeId, Long seatId) {
        Object locker = redisTemplate.opsForValue().get(lockKey(showtimeId, seatId));
        return locker != null && Objects.equals(String.valueOf(locker), String.valueOf(userId));
    }

    public String buildSeatInfo(List<Seat> seats) {
        return seats.stream()
                .sorted(Comparator.comparing(Seat::getRowNum).thenComparing(Seat::getColNum))
                .map(s -> s.getId() + ":" + s.getRowNum() + "排" + s.getColNum() + "座")
                .collect(Collectors.joining(","));
    }

    public List<Long> parseSeatIds(String seatInfo) {
        List<Long> ids = new ArrayList<>();
        if (seatInfo == null || seatInfo.trim().isEmpty()) {
            return ids;
        }
        String[] parts = seatInfo.split(",");
        for (String item : parts) {
            String[] pair = item.split(":");
            if (pair.length > 0) {
                ids.add(Long.parseLong(pair[0]));
            }
        }
        return ids;
    }

    private void initSeats(Long showtimeId, Showtime showtime) {
        Long count = seatMapper.selectCount(new LambdaQueryWrapper<Seat>().eq(Seat::getShowtimeId, showtimeId));
        if (count != null && count > 0) {
            return;
        }
        Hall hall = hallMapper.selectById(showtime.getHallId());
        if (hall == null) {
            throw new BusinessException("影厅不存在");
        }
        int rows = hall.getSeatRows() == null || hall.getSeatRows() <= 0 ? 8 : hall.getSeatRows();
        int cols = hall.getSeatCols() == null || hall.getSeatCols() <= 0 ? 10 : hall.getSeatCols();
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                Seat seat = new Seat();
                seat.setShowtimeId(showtimeId);
                seat.setRowNum(i);
                seat.setColNum(j);
                seat.setSeatType("NORMAL");
                seat.setPrice(showtime.getPrice());
                seat.setStatus("AVAILABLE");
                seatMapper.insert(seat);
            }
        }
        if (showtime.getAvailableSeats() == null || showtime.getAvailableSeats() <= 0) {
            showtime.setAvailableSeats(rows * cols);
            showtimeMapper.updateById(showtime);
        }
    }

    private String lockKey(Long showtimeId, Long seatId) {
        return "ticket:seat:lock:" + showtimeId + ":" + seatId;
    }
}
