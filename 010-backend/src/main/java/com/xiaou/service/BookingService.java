package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.*;
import com.xiaou.exception.BusinessException;
import com.xiaou.mapper.BookingMapper;
import com.xiaou.mapper.SeatMapper;
import com.xiaou.mapper.TimeSlotMapper;
import com.xiaou.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingMapper bookingMapper;
    
    @Autowired
    private SeatMapper seatMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private TimeSlotMapper timeSlotMapper;
    
    @Autowired
    private SeatService seatService;
    
    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Booking create(Booking booking, Long userId) {
        User user = userMapper.selectById(userId);
        
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        if (user.getBanUntil() != null && user.getBanUntil().isAfter(LocalDateTime.now())) {
            throw new BusinessException("账号已被临时禁用");
        }
        
        Integer creditScore = user.getCreditScore();
        if (creditScore < 60) {
            LambdaQueryWrapper<Booking> todayWrapper = new LambdaQueryWrapper<>();
            todayWrapper.eq(Booking::getUserId, userId);
            todayWrapper.eq(Booking::getBookingDate, booking.getBookingDate());
            todayWrapper.in(Booking::getStatus, "WAIT_CHECKIN", "IN_USE");
            if (bookingMapper.selectCount(todayWrapper) >= 1) {
                throw new BusinessException("信用分低于60分，每天只能预约1次");
            }
        }
        
        LambdaQueryWrapper<Booking> conflictWrapper = new LambdaQueryWrapper<>();
        conflictWrapper.eq(Booking::getUserId, userId);
        conflictWrapper.eq(Booking::getBookingDate, booking.getBookingDate());
        conflictWrapper.eq(Booking::getTimeSlot, booking.getTimeSlot());
        conflictWrapper.in(Booking::getStatus, "WAIT_CHECKIN", "IN_USE");
        if (bookingMapper.selectCount(conflictWrapper) > 0) {
            throw new BusinessException("该时段已有预约");
        }
        
        LambdaQueryWrapper<Booking> seatWrapper = new LambdaQueryWrapper<>();
        seatWrapper.eq(Booking::getSeatId, booking.getSeatId());
        seatWrapper.eq(Booking::getBookingDate, booking.getBookingDate());
        seatWrapper.eq(Booking::getTimeSlot, booking.getTimeSlot());
        seatWrapper.in(Booking::getStatus, "WAIT_CHECKIN", "IN_USE");
        if (bookingMapper.selectCount(seatWrapper) > 0) {
            throw new BusinessException("该座位已被预约");
        }
        
        TimeSlot timeSlot = getTimeSlotByCode(booking.getTimeSlot());
        LocalDateTime startTime = LocalDateTime.of(booking.getBookingDate(), timeSlot.getStartTime());
        LocalDateTime endTime = LocalDateTime.of(booking.getBookingDate(), timeSlot.getEndTime());
        
        booking.setUserId(userId);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setStatus("WAIT_CHECKIN");
        booking.setCreateTime(LocalDateTime.now());
        booking.setUpdateTime(LocalDateTime.now());
        bookingMapper.insert(booking);
        
        seatService.updateHotScore(booking.getSeatId());
        
        notificationService.sendNotification(userId, "预约成功", "您已成功预约座位，请在预约开始后15分钟内签到", "BOOKING");
        
        return booking;
    }

    public List<Booking> listMyBookings(Long userId) {
        LambdaQueryWrapper<Booking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Booking::getUserId, userId);
        wrapper.orderByDesc(Booking::getCreateTime);
        return bookingMapper.selectList(wrapper);
    }

    public Booking getById(Long id) {
        return bookingMapper.selectById(id);
    }

    @Transactional
    public void cancel(Long bookingId, Long userId, String reason) {
        Booking booking = bookingMapper.selectById(bookingId);
        if (!booking.getUserId().equals(userId)) {
            throw new BusinessException("无权操作");
        }
        if (!"WAIT_CHECKIN".equals(booking.getStatus())) {
            throw new BusinessException("只能取消待签到的预约");
        }
        
        LocalDateTime now = LocalDateTime.now();
        long minutesBefore = java.time.Duration.between(now, booking.getStartTime()).toMinutes();
        
        booking.setStatus("CANCELED");
        booking.setCancelTime(now);
        booking.setCancelReason(reason);
        booking.setUpdateTime(now);
        bookingMapper.updateById(booking);
        
        if (minutesBefore < 30) {
            User user = userMapper.selectById(userId);
            user.setCreditScore(user.getCreditScore() - 2);
            user.setUpdateTime(now);
            userMapper.updateById(user);
            
            notificationService.sendNotification(userId, "取消预约扣分", "提前30分钟内取消预约，扣除2分信用分", "CREDIT");
        }
    }

    @Transactional
    public void checkIn(Long bookingId, Long userId, String seatNo) {
        Booking booking = bookingMapper.selectById(bookingId);
        if (!booking.getUserId().equals(userId)) {
            throw new BusinessException("无权操作");
        }
        if (!"WAIT_CHECKIN".equals(booking.getStatus())) {
            throw new BusinessException("当前状态无法签到");
        }
        
        Seat seat = seatMapper.selectById(booking.getSeatId());
        if (!seat.getSeatNo().equals(seatNo)) {
            throw new BusinessException("座位编号不匹配");
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(booking.getStartTime().minusMinutes(15))) {
            throw new BusinessException("签到时间未到");
        }
        if (now.isAfter(booking.getStartTime().plusMinutes(15))) {
            throw new BusinessException("签到时间已过");
        }
        
        booking.setCheckInTime(now);
        booking.setStatus("IN_USE");
        booking.setUpdateTime(now);
        bookingMapper.updateById(booking);
        
        notificationService.sendNotification(userId, "签到成功", "您已成功签到，请按时签退", "CHECKIN");
    }

    @Transactional
    public void checkOut(Long bookingId, Long userId) {
        Booking booking = bookingMapper.selectById(bookingId);
        if (!booking.getUserId().equals(userId)) {
            throw new BusinessException("无权操作");
        }
        if (!"IN_USE".equals(booking.getStatus())) {
            throw new BusinessException("当前状态无法签退");
        }
        
        LocalDateTime now = LocalDateTime.now();
        booking.setCheckOutTime(now);
        booking.setStatus("COMPLETED");
        booking.setUpdateTime(now);
        bookingMapper.updateById(booking);
        
        User user = userMapper.selectById(userId);
        if (user.getCreditScore() < 100) {
            user.setCreditScore(user.getCreditScore() + 2);
            user.setUpdateTime(now);
            userMapper.updateById(user);
        }
        
        notificationService.sendNotification(userId, "签退成功", "感谢您的使用，信用分+2", "CREDIT");
    }

    public Booking getCurrentBooking(Long userId) {
        LambdaQueryWrapper<Booking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Booking::getUserId, userId);
        wrapper.eq(Booking::getStatus, "IN_USE");
        wrapper.orderByDesc(Booking::getCreateTime);
        wrapper.last("LIMIT 1");
        return bookingMapper.selectOne(wrapper);
    }

    @Transactional
    public void leave(Long bookingId, Long userId) {
        Booking booking = bookingMapper.selectById(bookingId);
        if (!booking.getUserId().equals(userId)) {
            throw new BusinessException("无权操作");
        }
        if (!"IN_USE".equals(booking.getStatus())) {
            throw new BusinessException("当前状态无法标记离开");
        }
        
        booking.setLeaveTime(LocalDateTime.now());
        booking.setUpdateTime(LocalDateTime.now());
        bookingMapper.updateById(booking);
        
        notificationService.sendNotification(userId, "临时离开", "请在30分钟内返回，否则将自动签退并扣除信用分", "LEAVE");
    }

    @Transactional
    public void returnBack(Long bookingId, Long userId) {
        Booking booking = bookingMapper.selectById(bookingId);
        if (!booking.getUserId().equals(userId)) {
            throw new BusinessException("无权操作");
        }
        if (!"IN_USE".equals(booking.getStatus())) {
            throw new BusinessException("当前状态无法标记返回");
        }
        if (booking.getLeaveTime() == null) {
            throw new BusinessException("未标记离开");
        }
        
        booking.setLeaveTime(null);
        booking.setUpdateTime(LocalDateTime.now());
        bookingMapper.updateById(booking);
        
        notificationService.sendNotification(userId, "返回成功", "您已返回座位", "RETURN");
    }

    private TimeSlot getTimeSlotByCode(String code) {
        LambdaQueryWrapper<TimeSlot> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TimeSlot::getSlotCode, code);
        return timeSlotMapper.selectOne(wrapper);
    }

    public List<Booking> listAllBookings() {
        LambdaQueryWrapper<Booking> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Booking::getCreateTime);
        return bookingMapper.selectList(wrapper);
    }
}

