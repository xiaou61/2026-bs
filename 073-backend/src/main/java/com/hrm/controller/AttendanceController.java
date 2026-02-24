package com.hrm.controller;

import com.hrm.common.Result;
import com.hrm.entity.Attendance;
import com.hrm.entity.User;
import com.hrm.service.AttendanceService;
import com.hrm.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Resource
    private AttendanceService attendanceService;
    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(attendanceService.getById(id));
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) Long employeeId,
                          @RequestParam(required = false) String employeeName,
                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                          @RequestParam(required = false) String status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(attendanceService.getList(employeeId, employeeName, startDate, endDate, status, pageNum, pageSize));
    }

    @PostMapping("/clockIn")
    public Result clockIn(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getById(userId);
        attendanceService.clockIn(user.getEmployeeId());
        return Result.success();
    }

    @PostMapping("/clockOut")
    public Result clockOut(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getById(userId);
        attendanceService.clockOut(user.getEmployeeId());
        return Result.success();
    }

    @GetMapping("/today")
    public Result getTodayAttendance(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getById(userId);
        return Result.success(attendanceService.getTodayAttendance(user.getEmployeeId()));
    }

    @GetMapping("/statistics")
    public Result getTodayStatistics() {
        return Result.success(attendanceService.getTodayStatistics());
    }

    @PostMapping
    public Result add(@RequestBody Attendance attendance) {
        attendanceService.add(attendance);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Attendance attendance) {
        attendanceService.update(attendance);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        attendanceService.delete(id);
        return Result.success();
    }
}
