package com.xiaou.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.Booking;
import com.xiaou.mapper.BookingMapper;
import com.xiaou.service.NotificationService;
import com.xiaou.service.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookingTask {

    @Autowired
    private BookingMapper bookingMapper;
    
    @Autowired
    private ViolationService violationService;
    
    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 * * * * ?")
    public void checkCheckinTimeout() {
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<Booking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Booking::getStatus, "WAIT_CHECKIN");
        wrapper.lt(Booking::getStartTime, now.minusMinutes(15));
        List<Booking> bookings = bookingMapper.selectList(wrapper);
        
        for (Booking booking : bookings) {
            booking.setStatus("RELEASED");
            booking.setUpdateTime(now);
            bookingMapper.updateById(booking);
            
            violationService.createViolation(booking.getUserId(), booking.getId(), "NO_CHECKIN", 5);
        }
        
        if (!bookings.isEmpty()) {
            System.out.println("自动释放超时未签到座位：" + bookings.size() + "个");
        }
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void checkCheckoutTimeout() {
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<Booking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Booking::getStatus, "IN_USE");
        wrapper.lt(Booking::getEndTime, now.minusMinutes(30));
        List<Booking> bookings = bookingMapper.selectList(wrapper);
        
        for (Booking booking : bookings) {
            booking.setStatus("COMPLETED");
            booking.setCheckOutTime(now);
            booking.setUpdateTime(now);
            bookingMapper.updateById(booking);
            
            violationService.createViolation(booking.getUserId(), booking.getId(), "NO_CHECKOUT", 3);
        }
        
        if (!bookings.isEmpty()) {
            System.out.println("自动签退超时未签退：" + bookings.size() + "个");
        }
    }

    @Scheduled(cron = "0 * * * * ?")
    public void sendCheckinReminder() {
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<Booking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Booking::getStatus, "WAIT_CHECKIN");
        wrapper.between(Booking::getStartTime, now.minusMinutes(11), now.minusMinutes(9));
        List<Booking> bookings = bookingMapper.selectList(wrapper);
        
        for (Booking booking : bookings) {
            notificationService.sendNotification(
                booking.getUserId(), 
                "签到提醒", 
                "您的预约即将开始，请在15分钟内完成签到", 
                "REMINDER"
            );
        }
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void sendCheckoutReminder() {
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<Booking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Booking::getStatus, "IN_USE");
        wrapper.between(Booking::getEndTime, now.plusMinutes(9), now.plusMinutes(11));
        List<Booking> bookings = bookingMapper.selectList(wrapper);
        
        for (Booking booking : bookings) {
            notificationService.sendNotification(
                booking.getUserId(), 
                "签退提醒", 
                "您的预约即将结束，请及时签退", 
                "REMINDER"
            );
        }
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void checkLeaveTimeout() {
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<Booking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Booking::getStatus, "IN_USE");
        wrapper.isNotNull(Booking::getLeaveTime);
        wrapper.lt(Booking::getLeaveTime, now.minusMinutes(30));
        List<Booking> bookings = bookingMapper.selectList(wrapper);
        
        for (Booking booking : bookings) {
            booking.setStatus("COMPLETED");
            booking.setCheckOutTime(now);
            booking.setUpdateTime(now);
            bookingMapper.updateById(booking);
            
            violationService.createViolation(booking.getUserId(), booking.getId(), "LEAVE_TIMEOUT", 3);
        }
        
        if (!bookings.isEmpty()) {
            System.out.println("临时离开超时自动签退：" + bookings.size() + "个");
        }
    }
}

