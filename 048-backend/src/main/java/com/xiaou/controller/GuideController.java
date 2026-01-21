package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.service.GuideBookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guide")
@RequiredArgsConstructor
public class GuideController {
    private final GuideBookingService guideBookingService;

    @GetMapping("/bookings")
    public Result<?> bookings(HttpServletRequest request,
                              @RequestParam(defaultValue = "1") int current,
                              @RequestParam(defaultValue = "10") int size) {
        Long guideId = (Long) request.getAttribute("userId");
        return Result.success(guideBookingService.pageByGuide(guideId, current, size));
    }

    @PostMapping("/booking/{id}/confirm")
    public Result<?> confirm(@PathVariable Long id) {
        guideBookingService.confirm(id);
        return Result.success();
    }

    @PostMapping("/booking/{id}/complete")
    public Result<?> complete(@PathVariable Long id) {
        guideBookingService.complete(id);
        return Result.success();
    }
}
