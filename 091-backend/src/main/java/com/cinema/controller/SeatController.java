package com.cinema.controller;

import com.cinema.common.Result;
import com.cinema.dto.SeatLockDTO;
import com.cinema.service.SeatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/seat")
public class SeatController {

    @Resource
    private SeatService seatService;

    @GetMapping("/showtime/{showtimeId}")
    public Result<?> list(@PathVariable Long showtimeId) {
        return Result.success(seatService.listByShowtime(showtimeId));
    }

    @PostMapping("/lock")
    public Result<?> lock(@RequestBody SeatLockDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        seatService.lock(userId, dto.getShowtimeId(), dto.getSeatIds());
        return Result.success();
    }

    @PostMapping("/unlock")
    public Result<?> unlock(@RequestBody SeatLockDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        seatService.unlockByUser(userId, dto.getShowtimeId(), dto.getSeatIds());
        return Result.success();
    }
}
