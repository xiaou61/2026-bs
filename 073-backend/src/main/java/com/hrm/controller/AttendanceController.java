package com.hrm.controller;

import com.hrm.common.BusinessException;
import com.hrm.common.Result;
import com.hrm.entity.Attendance;
import com.hrm.entity.User;
import com.hrm.service.AttendanceService;
import com.hrm.service.UserService;
import com.hrm.utils.AuthUtils;
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
    public Result getById(@PathVariable Long id, HttpServletRequest request) {
        Attendance attendance = attendanceService.getById(id);
        requireOwnAttendanceOrHr(request, attendance);
        return Result.success(attendance);
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) Long employeeId,
                          @RequestParam(required = false) String employeeName,
                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                          @RequestParam(required = false) String status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          HttpServletRequest request) {
        if (!AuthUtils.isAdminOrHr(request)) {
            employeeId = currentEmployeeId(request);
            employeeName = null;
        }
        return Result.success(attendanceService.getList(employeeId, employeeName, startDate, endDate, status, pageNum, pageSize));
    }

    @PostMapping("/clockIn")
    public Result clockIn(HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
        User user = userService.getById(userId);
        attendanceService.clockIn(user.getEmployeeId());
        return Result.success();
    }

    @PostMapping("/clockOut")
    public Result clockOut(HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
        User user = userService.getById(userId);
        attendanceService.clockOut(user.getEmployeeId());
        return Result.success();
    }

    @GetMapping("/today")
    public Result getTodayAttendance(HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
        User user = userService.getById(userId);
        return Result.success(attendanceService.getTodayAttendance(user.getEmployeeId()));
    }

    @GetMapping("/statistics")
    public Result getTodayStatistics(HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        return Result.success(attendanceService.getTodayStatistics());
    }

    @PostMapping
    public Result add(@RequestBody Attendance attendance, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        attendanceService.add(attendance);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Attendance attendance, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        attendanceService.update(attendance);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        attendanceService.delete(id);
        return Result.success();
    }

    private Long currentEmployeeId(HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
        User user = userService.getById(userId);
        if (user == null || user.getEmployeeId() == null) {
            throw new BusinessException(403, "当前账号未绑定员工档案");
        }
        return user.getEmployeeId();
    }

    private void requireOwnAttendanceOrHr(HttpServletRequest request, Attendance attendance) {
        if (attendance == null) {
            throw new BusinessException(404, "考勤记录不存在");
        }
        if (!AuthUtils.isAdminOrHr(request) && !attendance.getEmployeeId().equals(currentEmployeeId(request))) {
            throw new BusinessException(403, "只能访问自己的考勤记录");
        }
    }
}
