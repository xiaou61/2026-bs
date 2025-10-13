package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Booking;
import com.xiaou.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public Result<Booking> create(@RequestBody Booking booking, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Booking created = bookingService.create(booking, userId);
        return Result.success(created);
    }

    @GetMapping("/my-list")
    public Result<List<Booking>> listMyBookings(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Booking> bookings = bookingService.listMyBookings(userId);
        return Result.success(bookings);
    }

    @GetMapping("/{id}")
    public Result<Booking> getById(@PathVariable Long id) {
        Booking booking = bookingService.getById(id);
        return Result.success(booking);
    }

    @PutMapping("/{id}/cancel")
    public Result<String> cancel(@PathVariable Long id, @RequestBody Map<String, String> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String reason = params.get("reason");
        bookingService.cancel(id, userId, reason);
        return Result.success("取消成功");
    }

    @PostMapping("/{id}/check-in")
    public Result<String> checkIn(@PathVariable Long id, @RequestBody Map<String, String> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String seatNo = params.get("seatNo");
        bookingService.checkIn(id, userId, seatNo);
        return Result.success("签到成功");
    }

    @PostMapping("/{id}/check-out")
    public Result<String> checkOut(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        bookingService.checkOut(id, userId);
        return Result.success("签退成功");
    }

    @GetMapping("/current")
    public Result<Booking> getCurrentBooking(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Booking booking = bookingService.getCurrentBooking(userId);
        return Result.success(booking);
    }

    @PostMapping("/{id}/leave")
    public Result<String> leave(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        bookingService.leave(id, userId);
        return Result.success("已标记临时离开");
    }

    @PostMapping("/{id}/return")
    public Result<String> returnBack(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        bookingService.returnBack(id, userId);
        return Result.success("已返回座位");
    }
}

