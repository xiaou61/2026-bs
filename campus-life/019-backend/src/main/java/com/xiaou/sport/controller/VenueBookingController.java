package com.xiaou.sport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.sport.common.Result;
import com.xiaou.sport.entity.Venue;
import com.xiaou.sport.entity.VenueBooking;
import com.xiaou.sport.service.VenueBookingService;
import com.xiaou.sport.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/booking")
public class VenueBookingController {

    @Autowired
    private VenueBookingService venueBookingService;

    @Autowired
    private VenueService venueService;

    @PostMapping("/create")
    public Result<Void> createBooking(@RequestAttribute Long userId, @RequestBody VenueBooking booking) {
        booking.setUserId(userId);
        if (booking.getBookingDate() == null || booking.getStartTime() == null || booking.getEndTime() == null) {
            return Result.error("预约时间不能为空");
        }
        if (booking.getBookingDate().isBefore(LocalDate.now())) {
            return Result.error("不能预约过去的日期");
        }
        if (!booking.getEndTime().isAfter(booking.getStartTime())) {
            return Result.error("结束时间必须晚于开始时间");
        }

        Venue venue = venueService.getById(booking.getVenueId());
        if (venue == null) {
            return Result.error("场馆不存在");
        }
        if (venue.getOpeningTime() != null && booking.getStartTime().isBefore(venue.getOpeningTime())) {
            return Result.error("开始时间不能早于场馆开放时间");
        }
        if (venue.getClosingTime() != null && booking.getEndTime().isAfter(venue.getClosingTime())) {
            return Result.error("结束时间不能晚于场馆关闭时间");
        }

        LambdaQueryWrapper<VenueBooking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VenueBooking::getVenueId, booking.getVenueId())
                .eq(VenueBooking::getBookingDate, booking.getBookingDate())
                .ne(VenueBooking::getStatus, "cancelled")
                .and(item -> item.lt(VenueBooking::getStartTime, booking.getEndTime())
                        .gt(VenueBooking::getEndTime, booking.getStartTime()));
        if (venueBookingService.count(wrapper) > 0) {
            return Result.error("该时段已被预约");
        }

        Duration duration = Duration.between(booking.getStartTime(), booking.getEndTime());
        if (duration.toMinutes() <= 0) {
            return Result.error("预约时长必须大于0");
        }
        BigDecimal hours = new BigDecimal(duration.toMinutes()).divide(new BigDecimal(60), 1,
                java.math.RoundingMode.HALF_UP);
        booking.setDurationHours(hours);
        booking.setTotalPrice(venue.getPricePerHour().multiply(hours));
        booking.setStatus("confirmed");

        if (!venueBookingService.save(booking)) {
            return Result.error("预约失败");
        }
        return Result.success();
    }

    @GetMapping("/my")
    public Result<Page<VenueBooking>> myBookings(@RequestAttribute Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<VenueBooking> pageInfo = new Page<>(page, size);
        LambdaQueryWrapper<VenueBooking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VenueBooking::getUserId, userId)
                .orderByDesc(VenueBooking::getBookingDate);
        venueBookingService.page(pageInfo, wrapper);
        return Result.success(pageInfo);
    }

    @PutMapping("/{id}/checkin")
    public Result<Void> checkin(@PathVariable Long id, @RequestAttribute Long userId) {
        LambdaQueryWrapper<VenueBooking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VenueBooking::getId, id)
                .eq(VenueBooking::getUserId, userId);
        VenueBooking booking = venueBookingService.getOne(wrapper);
        if (booking == null) {
            return Result.error("预约不存在");
        }
        if (!"confirmed".equals(booking.getStatus())) {
            return Result.error("当前状态不可签到");
        }
        booking.setStatus("checked_in");
        venueBookingService.updateById(booking);
        return Result.success();
    }

    @PutMapping("/{id}/cancel")
    public Result<Void> cancel(@PathVariable Long id, @RequestAttribute Long userId, @RequestBody VenueBooking cancelData) {
        LambdaQueryWrapper<VenueBooking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VenueBooking::getId, id)
                .eq(VenueBooking::getUserId, userId);
        VenueBooking booking = venueBookingService.getOne(wrapper);
        if (booking == null) {
            return Result.error("预约不存在");
        }
        if ("cancelled".equals(booking.getStatus()) || "completed".equals(booking.getStatus())) {
            return Result.error("当前预约不可取消");
        }
        booking.setStatus("cancelled");
        booking.setCancelReason(cancelData.getCancelReason());
        venueBookingService.updateById(booking);
        return Result.success();
    }
}
