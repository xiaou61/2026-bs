package com.hospital.controller;

import com.github.pagehelper.PageInfo;
import com.hospital.common.Result;
import com.hospital.entity.DoctorSchedule;
import com.hospital.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/page")
    public Result<PageInfo<DoctorSchedule>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(required = false) Long departmentId,
                                                 @RequestParam(required = false) Long doctorId,
                                                 @RequestParam(required = false) Integer status,
                                                 @RequestParam(required = false) String scheduleDate,
                                                 @RequestAttribute("role") String role) {
        LocalDate date = scheduleDate == null || scheduleDate.isEmpty() ? null : LocalDate.parse(scheduleDate);
        return Result.success(scheduleService.page(departmentId, doctorId, status, date, pageNum, pageSize, role));
    }

    @GetMapping("/my")
    public Result<PageInfo<DoctorSchedule>> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(required = false) Integer status,
                                                   @RequestParam(required = false) String scheduleDate,
                                                   @RequestAttribute("userId") Long userId) {
        LocalDate date = scheduleDate == null || scheduleDate.isEmpty() ? null : LocalDate.parse(scheduleDate);
        return Result.success(scheduleService.myPage(userId, status, date, pageNum, pageSize));
    }

    @GetMapping("/public/list")
    public Result<List<DoctorSchedule>> publicList(@RequestParam(required = false) Long departmentId,
                                                   @RequestParam(required = false) Long doctorId,
                                                   @RequestParam(required = false) String scheduleDate) {
        LocalDate date = scheduleDate == null || scheduleDate.isEmpty() ? null : LocalDate.parse(scheduleDate);
        return Result.success(scheduleService.publicList(departmentId, doctorId, date));
    }

    @PostMapping
    public Result<String> add(@RequestBody DoctorSchedule entity,
                              @RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role) {
        scheduleService.save(entity, userId, role);
        return Result.success();
    }

    @PutMapping
    public Result<String> update(@RequestBody DoctorSchedule entity,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        scheduleService.save(entity, userId, role);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        scheduleService.delete(id, userId, role);
        return Result.success();
    }
}
