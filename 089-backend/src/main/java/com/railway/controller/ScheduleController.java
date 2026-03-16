package com.railway.controller;

import com.railway.common.Result;
import com.railway.entity.Schedule;
import com.railway.service.ScheduleService;
import com.railway.utils.AuthUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Resource
    private ScheduleService scheduleService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long trainId,
                          @RequestParam(required = false) Long departureStationId,
                          @RequestParam(required = false) Long arrivalStationId,
                          @RequestParam(required = false) String travelDate,
                          @RequestParam(required = false) String saleStatus,
                          @RequestParam(required = false) Integer status) {
        return Result.success(scheduleService.page(pageNum, pageSize, trainId, departureStationId, arrivalStationId, travelDate, saleStatus, status));
    }

    @GetMapping("/enabled")
    public Result<?> enabled() {
        return Result.success(scheduleService.enabledList());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Schedule schedule, HttpServletRequest request) {
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        scheduleService.save(schedule);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        scheduleService.deleteById(id);
        return Result.success();
    }
}
