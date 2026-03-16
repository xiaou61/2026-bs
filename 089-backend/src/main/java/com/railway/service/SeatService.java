package com.railway.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.railway.common.BusinessException;
import com.railway.entity.Carriage;
import com.railway.entity.Schedule;
import com.railway.entity.Seat;
import com.railway.mapper.SeatMapper;
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
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private CarriageService carriageService;

    public List<Seat> listBySchedule(Long scheduleId) {
        Schedule schedule = scheduleService.getById(scheduleId);
        initSeats(scheduleId, schedule);
        return seatMapper.selectList(new LambdaQueryWrapper<Seat>()
                .eq(Seat::getScheduleId, scheduleId)
                .orderByAsc(Seat::getCoachNo, Seat::getRowNum, Seat::getColNum));
    }

    public List<Seat> getSeats(Long scheduleId, List<Long> seatIds) {
        if (seatIds == null || seatIds.isEmpty()) {
            throw new BusinessException("座位不能为空");
        }
        List<Seat> seats = seatMapper.selectList(new LambdaQueryWrapper<Seat>()
                .eq(Seat::getScheduleId, scheduleId)
                .in(Seat::getId, seatIds));
        if (seats.size() != seatIds.size()) {
            throw new BusinessException("存在无效座位");
        }
        seats.sort(Comparator.comparing(Seat::getCoachNo).thenComparing(Seat::getRowNum).thenComparing(Seat::getColNum));
        return seats;
    }

    @Transactional(rollbackFor = Exception.class)
    public void lock(Long userId, Long scheduleId, List<Long> seatIds) {
        List<Seat> seats = getSeats(scheduleId, seatIds);
        for (Seat seat : seats) {
            if (!"AVAILABLE".equals(seat.getStatus())) {
                if (!"LOCKED".equals(seat.getStatus()) || !isLockedByUser(userId, scheduleId, seat.getId())) {
                    throw new BusinessException("座位已不可用: " + buildSeatLabel(seat));
                }
            }
            String key = lockKey(scheduleId, seat.getId());
            Object locker = redisTemplate.opsForValue().get(key);
            if (locker != null && !Objects.equals(String.valueOf(locker), String.valueOf(userId))) {
                throw new BusinessException("座位已被锁定: " + buildSeatLabel(seat));
            }
        }
        for (Seat seat : seats) {
            redisTemplate.opsForValue().set(lockKey(scheduleId, seat.getId()), userId, 15, TimeUnit.MINUTES);
            seat.setStatus("LOCKED");
            seatMapper.updateById(seat);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void unlockByUser(Long userId, Long scheduleId, List<Long> seatIds) {
        List<Seat> seats = getSeats(scheduleId, seatIds);
        for (Seat seat : seats) {
            String key = lockKey(scheduleId, seat.getId());
            Object locker = redisTemplate.opsForValue().get(key);
            if (locker != null && Objects.equals(String.valueOf(locker), String.valueOf(userId))) {
                redisTemplate.delete(key);
                seat.setStatus("AVAILABLE");
                seatMapper.updateById(seat);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void unlockForOrder(Long scheduleId, List<Long> seatIds) {
        List<Seat> seats = getSeats(scheduleId, seatIds);
        for (Seat seat : seats) {
            redisTemplate.delete(lockKey(scheduleId, seat.getId()));
            seat.setStatus("AVAILABLE");
            seatMapper.updateById(seat);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void occupy(Long scheduleId, List<Long> seatIds) {
        List<Seat> seats = getSeats(scheduleId, seatIds);
        for (Seat seat : seats) {
            if ("SOLD".equals(seat.getStatus())) {
                throw new BusinessException("座位已售出");
            }
            redisTemplate.delete(lockKey(scheduleId, seat.getId()));
            seat.setStatus("SOLD");
            seatMapper.updateById(seat);
        }
        scheduleService.changeAvailableSeats(scheduleId, -seats.size());
    }

    @Transactional(rollbackFor = Exception.class)
    public void releaseSold(Long scheduleId, List<Long> seatIds) {
        List<Seat> seats = getSeats(scheduleId, seatIds);
        int count = 0;
        for (Seat seat : seats) {
            if ("SOLD".equals(seat.getStatus())) {
                seat.setStatus("AVAILABLE");
                seatMapper.updateById(seat);
                count++;
            }
            redisTemplate.delete(lockKey(scheduleId, seat.getId()));
        }
        if (count > 0) {
            scheduleService.changeAvailableSeats(scheduleId, count);
        }
    }

    public boolean isLockedByUser(Long userId, Long scheduleId, Long seatId) {
        Object locker = redisTemplate.opsForValue().get(lockKey(scheduleId, seatId));
        return locker != null && Objects.equals(String.valueOf(locker), String.valueOf(userId));
    }

    public String buildSeatInfo(List<Seat> seats) {
        return seats.stream()
                .sorted(Comparator.comparing(Seat::getCoachNo).thenComparing(Seat::getRowNum).thenComparing(Seat::getColNum))
                .map(seat -> seat.getId() + ":" + buildSeatLabel(seat))
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

    public List<String> parseSeatLabels(String seatInfo) {
        List<String> labels = new ArrayList<>();
        if (seatInfo == null || seatInfo.trim().isEmpty()) {
            return labels;
        }
        String[] parts = seatInfo.split(",");
        for (String item : parts) {
            String[] pair = item.split(":");
            if (pair.length > 1) {
                labels.add(pair[1]);
            }
        }
        return labels;
    }

    private void initSeats(Long scheduleId, Schedule schedule) {
        Long count = seatMapper.selectCount(new LambdaQueryWrapper<Seat>().eq(Seat::getScheduleId, scheduleId));
        if (count != null && count > 0) {
            return;
        }
        Carriage carriage = carriageService.getById(schedule.getCarriageId());
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int coach = 1; coach <= carriage.getCoachCount(); coach++) {
            for (int row = 1; row <= carriage.getSeatRows(); row++) {
                for (int col = 1; col <= carriage.getSeatCols(); col++) {
                    Seat seat = new Seat();
                    seat.setScheduleId(scheduleId);
                    seat.setCoachNo(coach);
                    seat.setRowNum(row);
                    seat.setColNum(col);
                    seat.setSeatNo(col <= letters.length ? letters[col - 1] : String.valueOf(col));
                    seat.setSeatType(carriage.getSeatType());
                    seat.setPrice(schedule.getBasePrice().multiply(carriage.getPriceFactor()));
                    seat.setStatus("AVAILABLE");
                    seatMapper.insert(seat);
                }
            }
        }
    }

    private String buildSeatLabel(Seat seat) {
        return seat.getCoachNo() + "车" + seat.getRowNum() + "排" + seat.getSeatNo() + "座";
    }

    private String lockKey(Long scheduleId, Long seatId) {
        return "railway:seat:lock:" + scheduleId + ":" + seatId;
    }
}
