package com.oa.controller;

import com.oa.common.Result;
import com.oa.service.AttendanceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @GetMapping("/today")
    public Result today(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(attendanceService.getToday(userId));
    }

    @PostMapping("/clockIn")
    public Result clockIn(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        attendanceService.clockIn(userId);
        return Result.success();
    }

    @PostMapping("/clockOut")
    public Result clockOut(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        attendanceService.clockOut(userId);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String date, String keyword) {
        return Result.success(attendanceService.getList(pageNum, pageSize, date, keyword));
    }

    @GetMapping("/my")
    public Result my(HttpServletRequest request,
                     @RequestParam(defaultValue = "1") Integer pageNum,
                     @RequestParam(defaultValue = "10") Integer pageSize,
                     String month) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(attendanceService.getMyList(userId, pageNum, pageSize, month));
    }

    @GetMapping("/todayCount")
    public Result todayCount() {
        return Result.success(attendanceService.getTodayCount());
    }
}
