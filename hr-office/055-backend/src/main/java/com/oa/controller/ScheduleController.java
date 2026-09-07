package com.oa.controller;

import com.oa.common.Result;
import com.oa.entity.Schedule;
import com.oa.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/list")
    public Result list(HttpServletRequest request, String date) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(scheduleService.getList(userId, date));
    }

    @PostMapping
    public Result add(HttpServletRequest request, @RequestBody Schedule schedule) {
        Long userId = (Long) request.getAttribute("userId");
        scheduleService.add(userId, schedule);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Schedule schedule) {
        scheduleService.update(schedule);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return Result.success();
    }
}
