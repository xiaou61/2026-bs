package com.psychology.system.controller;

import com.psychology.system.common.Result;
import com.psychology.system.entity.Counselor;
import com.psychology.system.entity.TimeSlot;
import com.psychology.system.service.CounselorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/counselors")
@CrossOrigin
@RequiredArgsConstructor
public class CounselorController {
    private final CounselorService counselorService;

    @GetMapping
    public Result<List<Counselor>> getCounselors() {
        return Result.success(counselorService.getCounselors());
    }

    @GetMapping("/{id}")
    public Result<Counselor> getCounselorById(@PathVariable Long id) {
        return Result.success(counselorService.getCounselorById(id));
    }

    @GetMapping("/{id}/timeslots")
    public Result<List<TimeSlot>> getTimeSlots(@PathVariable Long id) {
        return Result.success(counselorService.getTimeSlots(id));
    }
}
