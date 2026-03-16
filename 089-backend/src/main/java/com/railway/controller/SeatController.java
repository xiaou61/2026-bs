package com.railway.controller;

import com.railway.common.Result;
import com.railway.dto.SeatLockDTO;
import com.railway.service.SeatService;
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

    @GetMapping("/schedule/{scheduleId}")
    public Result<?> list(@PathVariable Long scheduleId) {
        return Result.success(seatService.listBySchedule(scheduleId));
    }

    @PostMapping("/lock")
    public Result<?> lock(@RequestBody SeatLockDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        seatService.lock(userId, dto.getScheduleId(), dto.getSeatIds());
        return Result.success();
    }

    @PostMapping("/unlock")
    public Result<?> unlock(@RequestBody SeatLockDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        seatService.unlockByUser(userId, dto.getScheduleId(), dto.getSeatIds());
        return Result.success();
    }
}
