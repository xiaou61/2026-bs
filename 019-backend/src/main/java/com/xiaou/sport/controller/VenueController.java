package com.xiaou.sport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.sport.common.Result;
import com.xiaou.sport.entity.Venue;
import com.xiaou.sport.entity.VenueBooking;
import com.xiaou.sport.service.VenueBookingService;
import com.xiaou.sport.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/venue")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @Autowired
    private VenueBookingService venueBookingService;

    @GetMapping("/list")
    public Result<Page<Venue>> listVenues(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String venueType) {
        Page<Venue> pageInfo = new Page<>(page, size);
        LambdaQueryWrapper<Venue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Venue::getStatus, 0);
        if (venueType != null && !venueType.isEmpty()) {
            wrapper.eq(Venue::getVenueType, venueType);
        }
        venueService.page(pageInfo, wrapper);
        return Result.success(pageInfo);
    }

    @GetMapping("/{id}")
    public Result<Venue> getVenue(@PathVariable Long id) {
        Venue venue = venueService.getById(id);
        return Result.success(venue);
    }

    @GetMapping("/{id}/schedule")
    public Result<List<VenueBooking>> getVenueSchedule(@PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookingDate) {
        if (venueService.getById(id) == null) {
            return Result.error("场馆不存在");
        }

        LambdaQueryWrapper<VenueBooking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VenueBooking::getVenueId, id)
                .ne(VenueBooking::getStatus, "cancelled")
                .orderByAsc(VenueBooking::getBookingDate)
                .orderByAsc(VenueBooking::getStartTime);
        if (bookingDate != null) {
            wrapper.eq(VenueBooking::getBookingDate, bookingDate);
        }
        return Result.success(venueBookingService.list(wrapper));
    }
}
