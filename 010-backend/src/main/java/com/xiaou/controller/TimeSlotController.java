package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.TimeSlot;
import com.xiaou.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/timeslot")
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

    @GetMapping("/list")
    public Result<List<TimeSlot>> list() {
        List<TimeSlot> timeSlots = timeSlotService.listAll();
        return Result.success(timeSlots);
    }

    @GetMapping("/{code}")
    public Result<TimeSlot> getByCode(@PathVariable String code) {
        TimeSlot timeSlot = timeSlotService.getByCode(code);
        return Result.success(timeSlot);
    }
}

