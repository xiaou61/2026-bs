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
import java.time.LocalTime;

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

        Venue venue = venueService.getById(booking.getVenueId());
        if (venue == null) {
            return Result.error("场馆不存在");
        }

        Duration duration = Duration.between(booking.getStartTime(), booking.getEndTime());
        BigDecimal hours = new BigDecimal(duration.toMinutes()).divide(new BigDecimal(60), 1,
                java.math.RoundingMode.HALF_UP);
        booking.setDurationHours(hours);
        booking.setTotalPrice(venue.getPricePerHour().multiply(hours));
        booking.setStatus("pending");

        venueBookingService.save(booking);
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
    public Result<Void> checkin(@PathVariable Long id) {
        VenueBooking booking = venueBookingService.getById(id);
        booking.setStatus("checked_in");
        venueBookingService.updateById(booking);
        return Result.success();
    }

    @PutMapping("/{id}/cancel")
    public Result<Void> cancel(@PathVariable Long id, @RequestBody VenueBooking cancelData) {
        VenueBooking booking = venueBookingService.getById(id);
        booking.setStatus("cancelled");
        booking.setCancelReason(cancelData.getCancelReason());
        venueBookingService.updateById(booking);
        return Result.success();
    }
}
